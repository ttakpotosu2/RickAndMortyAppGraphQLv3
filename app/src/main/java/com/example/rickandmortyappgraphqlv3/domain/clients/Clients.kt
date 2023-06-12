package com.example.rickandmortyappgraphqlv3.domain.clients

import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.model.LocationResultEntity

interface Clients {

    suspend fun getCharacters(page: String): List<CharacterResultsEntity>
    suspend fun getEpisodes(page: String): List<EpisodesResultsEntity>
    suspend fun getLocations(page: Int?): List<LocationResultEntity>
}