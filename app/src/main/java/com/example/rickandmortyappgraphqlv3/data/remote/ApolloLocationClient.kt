package com.example.rickandmortyappgraphqlv3.data.remote

//import com.apollographql.apollo3.ApolloClient
//import com.example.rickandmortyappgraphqlv3.domain.clients.CharacterClient
//import com.example.rickandmortyappgraphqlv3.domain.clients.LocationClient
//import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
//import com.example.rickandmortyappgraphqlv3.domain.model.LocationResultEntity
//import com.example.rickandmortygraphqlv3.CharactersQuery
//import com.example.rickandmortygraphqlv3.LocationsQuery
//import javax.inject.Inject
//
//class ApolloLocationClient @Inject constructor(
//    private val apolloClient: ApolloClient
//): LocationClient {
//    override suspend fun getLocations(page: Int?): List<LocationResultEntity> {
//        return apolloClient
//            .query(LocationsQuery())
//            .execute()
//            .data
//            ?.locations
//            ?.results
//            ?.map { locations ->
//                LocationResultEntity (
//                    id = locations?.id.orEmpty(),
//                    name = locations?.name.orEmpty(),
//                    dimension = locations?.dimension.orEmpty(),
//                    type = locations?.type.orEmpty(),
//                    residents = locations?.residents?.mapNotNull { it?.id } ?: emptyList()
//                )
//            } ?: emptyList()
//    }
//}