package com.example.rickandmortyappgraphqlv3.domain.repository

import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity
import javax.inject.Inject

data class EpisodeAndCharacters(
    val episode: EpisodesResultsEntity,
    val characters: List<CharacterResultsEntity>
)

class EpisodeRepository @Inject constructor(
    private val database: RickAndMortyAppResultsDatabase
)  {

    suspend fun getEpisode(episodeId: String): EpisodeAndCharacters {
        val episode = database.episodesResultsDao().getSingleEpisode(episodeId)
        val characters = database.charactersResultsDao().getCharacterForEpisode(episode.characters)

        return EpisodeAndCharacters(
            episode = episode,
            characters = characters
        )
    }
}