package com.example.rickandmortyappgraphqlv3.data.remote

import com.apollographql.apollo3.ApolloClient
import com.example.rickandmortyappgraphqlv3.domain.clients.Clients
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.model.LocationResultEntity
import com.example.rickandmortygraphqlv3.CharactersQuery
import com.example.rickandmortygraphqlv3.EpisodesQuery
import com.example.rickandmortygraphqlv3.LocationsQuery
import javax.inject.Inject

class ApolloClients @Inject constructor(
    private val apolloClient: ApolloClient
): Clients {
    override suspend fun getCharacters(page: String): List<CharacterResultsEntity> {
        return apolloClient
            .query(CharactersQuery())
            .execute()
            .data
            ?.characters
            ?.results
            ?.map { characters ->
                CharacterResultsEntity(
                    id = characters?.id.orEmpty(),
                    name = characters?.name.orEmpty(),
                    image = characters?.image.orEmpty(),
                    status = characters?.status.orEmpty(),
                    species = characters?.species.orEmpty(),
                    gender = characters?.gender.orEmpty(),
                    episodes = characters?.episode?.mapNotNull { it?.id } ?: emptyList()
                )
            } ?: emptyList()
    }

    override suspend fun getEpisodes(page: String): List<EpisodesResultsEntity> {
        return apolloClient
            .query(EpisodesQuery())
            .execute()
            .data
            ?.episodes
            ?.results
            ?.map { episodes ->
                EpisodesResultsEntity(
                    id = episodes?.id.orEmpty(),
                    name = episodes?.name.orEmpty(),
                    episode = episodes?.episode.orEmpty(),
                    airDate = episodes?.air_date.orEmpty(),
                    characters = episodes?.characters?.mapNotNull { it?.id } ?: emptyList()
                )
            } ?: emptyList()
    }

    override suspend fun getLocations(page: Int?): List<LocationResultEntity> {
        return apolloClient
            .query(LocationsQuery())
            .execute()
            .data
            ?.locations
            ?.results
            ?.map { locations ->
                LocationResultEntity (
                    id = locations?.id.orEmpty(),
                    name = locations?.name.orEmpty(),
                    dimension = locations?.dimension.orEmpty(),
                    type = locations?.type.orEmpty(),
                    residents = locations?.residents?.mapNotNull { it?.id } ?: emptyList()
                )
            } ?: emptyList()
    }
}