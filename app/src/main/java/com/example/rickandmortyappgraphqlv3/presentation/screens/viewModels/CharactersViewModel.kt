package com.example.rickandmortyappgraphqlv3.presentation.screens.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import com.example.rickandmortyappgraphqlv3.data.paging.CharactersRemoteMediator
import com.example.rickandmortyappgraphqlv3.data.remote.ApolloClients
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val database: RickAndMortyAppResultsDatabase,
    client: ApolloClients
): ViewModel() {

        @OptIn(ExperimentalPagingApi::class)
        val execute: Flow<PagingData<CharacterResultsEntity>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {database.charactersResultsDao().getCharacters()},
        remoteMediator = CharactersRemoteMediator(client = client, database = database)
    ).flow.flowOn(Dispatchers.IO)
}