package com.example.rickandmortyappgraphqlv3.domain.repository

import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity
import javax.inject.Inject

data class CharacterAndEpisodes(
    val character: CharacterResultsEntity,
    val episodes: List<EpisodesResultsEntity>
)

class CharacterRepository @Inject constructor(
    private val database: RickAndMortyAppResultsDatabase
)  {

    suspend fun getCharacter(characterId: String): CharacterAndEpisodes {
        val character = database.charactersResultsDao().getCharacterById(characterId)
        val episodes = database.episodesResultsDao().getEpisodeForCharacter(character.episodes)

        return CharacterAndEpisodes(
            character = character,
            episodes = episodes
        )
    }
}