package com.example.rickandmortyappgraphqlv3.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import com.example.rickandmortyappgraphqlv3.data.paging.CharactersRemoteMediator
import com.example.rickandmortyappgraphqlv3.data.paging.LocationsRemoteMediator
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.model.LocationResultEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

data class LocationAndCharacters(
    val location: LocationResultEntity,
    val characters: List<CharacterResultsEntity>
)

class LocationRepository @Inject constructor(
    private val database: RickAndMortyAppResultsDatabase,
) {

    suspend fun getLocation(locationId: String): LocationAndCharacters {
        val location = database.locationsResultDao().getSingleLocation(locationId)
        val characters = database.charactersResultsDao().getCharacterForLocation(location.residents)

        return LocationAndCharacters(
            location = location,
            characters = characters,
        )
    }
}