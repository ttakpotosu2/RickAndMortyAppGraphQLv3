package com.example.rickandmortyappgraphqlv3.data.paging

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmortyappgraphqlv3.data.local.LocationResultsRemoteKeys
import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import com.example.rickandmortyappgraphqlv3.domain.clients.Clients
import com.example.rickandmortyappgraphqlv3.domain.model.LocationResultEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LocationsRemoteMediator @Inject constructor(
    private val client: Clients,
    private val database: RickAndMortyAppResultsDatabase
): RemoteMediator<Int, LocationResultEntity>() {

    private val locationResultsDao = database.locationsResultDao()
    private val locationResultsRemoteKeysDao = database.locationsResultsRemoteKeysDao()

    override suspend fun load( 
        loadType: LoadType,
        state: PagingState<Int, LocationResultEntity>
    ): MediatorResult {
        return try {
            val currentPage = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.next?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysForFirstItem(state)
                    val prevPage = remoteKeys?.prev
                        ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.next
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = client.getLocations(currentPage)
            // complete list of xters from api
            val allCharacters = client.getCharacters(currentPage.toString())

            val residentsIds = response.flatMap { it.residents }
            val residentsResponse = client.getCharacters(residentsIds.toString())

            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            // xter dto list from api minus list used in database
           val remainingCharacters = allCharacters - residentsResponse

            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    locationResultsDao.deleteLocations()
                    locationResultsRemoteKeysDao.deleteLocationsRemoteKeys()
                }
                val keys = response.map { location ->
                    LocationResultsRemoteKeys(
                        id = location.id.toInt(),
                        prev = prevPage,
                        next = nextPage
                    )
                }
                locationResultsRemoteKeysDao.addLocationsRemoteKeys(remoteKeys = keys)
                locationResultsDao.addLocations(locations = response)
                database.charactersResultsDao().addCharacters(residentsResponse)
                database.charactersResultsDao().updateCharacters(remainingCharacters)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocationResultEntity>
    ): LocationResultsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                locationResultsRemoteKeysDao.getLocationsRemoteKeys(id = it.toInt())
            }
        }
    }

    private fun getRemoteKeysForFirstItem(
        state: PagingState<Int, LocationResultEntity>
    ): LocationResultsRemoteKeys?{
        return state.pages.firstOrNull { it.data.isNotEmpty()}?.data?.firstOrNull()
            ?.let { locationResultsRemoteKeysDao.getLocationsRemoteKeys(id = it.id.toInt()) }
    }

    private fun getRemoteKeysForLastItem(
        state: PagingState<Int, LocationResultEntity>
    ): LocationResultsRemoteKeys?{
        return state.pages.lastOrNull { it.data.isNotEmpty()}?.data?.lastOrNull()
            ?.let { locationResultsRemoteKeysDao.getLocationsRemoteKeys(id = it.id.toInt()) }
    }
}