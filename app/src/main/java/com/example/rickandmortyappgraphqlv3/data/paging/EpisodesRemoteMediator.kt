package com.example.rickandmortyappgraphqlv3.data.paging

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmortyappgraphqlv3.data.local.EpisodesResultRemoteKeys
import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import com.example.rickandmortyappgraphqlv3.domain.clients.Clients
import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class EpisodesRemoteMediator @Inject constructor(
    private val client: Clients,
    private val database: RickAndMortyAppResultsDatabase
) : RemoteMediator<Int, EpisodesResultsEntity>() {

    private val episodesResultsDao = database.episodesResultsDao()
    private val episodesResultsRemoteKeysDao = database.episodesResultsRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodesResultsEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
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
            val response = client.getEpisodes(currentPage.toString())

            val allCharacters = client.getCharacters(currentPage.toString())

            val characterIds = response.flatMap { it.characters }
            val charactersResponse = client.getCharacters(characterIds.toString())

            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            val remainingCharacters = allCharacters - charactersResponse.toSet()

            database.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    episodesResultsDao.deleteEpisodes()
//                    episodesResultsRemoteKeysDao.deleteEpisodesRemoteKeys()
//                }
                val keys = response.map { episode ->
                    EpisodesResultRemoteKeys(
                        id = episode.id.toInt(),
                        prev = prevPage,
                        next = nextPage
                    )
                }
                episodesResultsRemoteKeysDao.addEpisodesRemoteKeys(remoteKeys = keys)
                episodesResultsDao.addEpisodes(episodes = response)
                database.charactersResultsDao().addCharacters(charactersResponse)
                database.charactersResultsDao().updateCharacters(remainingCharacters)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, EpisodesResultsEntity>
    ): EpisodesResultRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                episodesResultsRemoteKeysDao.getEpisodesRemoteKeys(id = it.toInt())
            }
        }
    }

    private fun getRemoteKeysForFirstItem(
        state: PagingState<Int, EpisodesResultsEntity>
    ): EpisodesResultRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { episodesResultsRemoteKeysDao.getEpisodesRemoteKeys(id = it.id.toInt()) }
    }

    private fun getRemoteKeysForLastItem(
        state: PagingState<Int, EpisodesResultsEntity>
    ): EpisodesResultRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { episodesResultsRemoteKeysDao.getEpisodesRemoteKeys(id = it.id.toInt()) }
    }
}