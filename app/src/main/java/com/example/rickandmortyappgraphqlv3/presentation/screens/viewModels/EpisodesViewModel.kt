package com.example.rickandmortyappgraphqlv3.presentation.screens.viewModels

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import com.example.rickandmortyappgraphqlv3.data.paging.EpisodesRemoteMediator
import com.example.rickandmortyappgraphqlv3.data.remote.ApolloClients
import com.example.rickandmortyappgraphqlv3.domain.clients.Clients
import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val database: RickAndMortyAppResultsDatabase,
    client: ApolloClients
): ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val execute: Flow<PagingData<EpisodesResultsEntity>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {database.episodesResultsDao().getEpisodes()},
        remoteMediator = EpisodesRemoteMediator(client = client, database = database)
    ).flow
}