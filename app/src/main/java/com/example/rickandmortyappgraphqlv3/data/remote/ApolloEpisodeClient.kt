package com.example.rickandmortyappgraphqlv3.data.remote

//import com.apollographql.apollo3.ApolloClient
//import com.example.rickandmortyappgraphqlv3.domain.clients.CharacterClient
//import com.example.rickandmortyappgraphqlv3.domain.clients.EpisodeClient
//import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
//import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity
//import com.example.rickandmortygraphqlv3.CharactersQuery
//import com.example.rickandmortygraphqlv3.EpisodesQuery
//import javax.inject.Inject
//
//class ApolloEpisodeClient @Inject constructor(
//    private val apolloClient: ApolloClient
//): EpisodeClient {
//    override suspend fun getEpisodes(page: Int?): List<EpisodesResultsEntity> {
//        return apolloClient
//            .query(EpisodesQuery())
//            .execute()
//            .data
//            ?.episodes
//            ?.results
//            ?.map { episodes ->
//                EpisodesResultsEntity(
//                    id = episodes?.id.orEmpty(),
//                    name = episodes?.name.orEmpty(),
//                    episode = episodes?.episode.orEmpty(),
//                    airDate = episodes?.air_date.orEmpty(),
//                    characters = episodes?.characters?.mapNotNull { it?.id } ?: emptyList()
//                )
//            } ?: emptyList()
//    }
//}