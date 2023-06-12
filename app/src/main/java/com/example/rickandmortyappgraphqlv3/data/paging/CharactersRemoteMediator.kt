package com.example.rickandmortyappgraphqlv3.data.paging

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmortyappgraphqlv3.data.local.CharactersResultsRemoteKeys
import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import com.example.rickandmortyappgraphqlv3.domain.clients.Clients
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator @Inject constructor(
    private val client: Clients,
    private val database: RickAndMortyAppResultsDatabase
) : RemoteMediator<Int, CharacterResultsEntity>() {

    private val charactersResultsDao = database.charactersResultsDao()
    private val charactersResultsRemoteKeysDao = database.charactersRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterResultsEntity>,
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.next?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prev
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.next
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = client.getCharacters(currentPage.toString())

            val allEpisodes = client.getEpisodes(currentPage.toString())

            val episodesIds = response.flatMap { it.episodes }
            val episodesResponse = client.getEpisodes(episodesIds.toString())

            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            val remainingEpisodes = allEpisodes - episodesResponse.toSet()

            database.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    charactersResultsDao.deleteCharacters()
//                    charactersResultsRemoteKeysDao.deleteCharactersRemoteKeys()
//                }
                val keys = response.map { character ->
                    CharactersResultsRemoteKeys(
                        id = character.id.toInt(),
                        prev = prevPage,
                        next = nextPage
                    )
                }
                charactersResultsRemoteKeysDao.addCharactersRemoteKeys(remoteKeys = keys)
                charactersResultsDao.addCharacters(characters = response)

                database.episodesResultsDao().addEpisodes(allEpisodes)

                database.episodesResultsDao().updateEpisodes(remainingEpisodes)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterResultsEntity>
    ): CharactersResultsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                charactersResultsRemoteKeysDao.getCharactersRemoteKeys(id = it.toInt())
            }
        }
    }

    private fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CharacterResultsEntity>
    ): CharactersResultsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { charactersResultsRemoteKeysDao.getCharactersRemoteKeys(id = it.id.toInt()) }
    }

    private fun getRemoteKeyForLastItem(
        state: PagingState<Int, CharacterResultsEntity>
    ): CharactersResultsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { charactersResultsRemoteKeysDao.getCharactersRemoteKeys(id = it.id.toInt()) }
    }
}