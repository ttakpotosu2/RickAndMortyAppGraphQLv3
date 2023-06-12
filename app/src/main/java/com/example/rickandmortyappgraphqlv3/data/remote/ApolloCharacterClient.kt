package com.example.rickandmortyappgraphqlv3.data.remote

//import com.apollographql.apollo3.ApolloClient
//import com.example.rickandmortyappgraphqlv3.domain.clients.CharacterClient
//import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
//import com.example.rickandmortygraphqlv3.CharactersQuery
//import javax.inject.Inject
//
//class ApolloCharacterClient @Inject constructor(
//    private val apolloClient: ApolloClient
//): CharacterClient {
//    override suspend fun getCharacters(page: Int?): List<CharacterResultsEntity> {
//        return apolloClient
//            .query(CharactersQuery())
//            .execute()
//            .data
//            ?.characters
//            ?.results
//            ?.map { characters ->
//                CharacterResultsEntity(
//                    id = characters?.id.orEmpty(),
//                    name = characters?.name.orEmpty(),
//                    image = characters?.image.orEmpty(),
//                    status = characters?.status.orEmpty(),
//                    species = characters?.species.orEmpty(),
//                    gender = characters?.gender.orEmpty(),
//                    episodes = characters?.episode?.mapNotNull { it?.id } ?: emptyList()
//                )
//            } ?: emptyList()
//    }
//}