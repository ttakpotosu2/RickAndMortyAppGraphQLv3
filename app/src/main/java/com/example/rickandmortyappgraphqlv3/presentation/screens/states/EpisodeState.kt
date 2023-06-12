package com.example.rickandmortyappgraphqlv3.presentation.screens.states

import com.example.rickandmortyappgraphqlv3.domain.repository.EpisodeAndCharacters

sealed class EpisodeState {
    object Loading: EpisodeState()
    data class Success(val episode: EpisodeAndCharacters): EpisodeState()
    class Error(val message: String? = null) : EpisodeState()
}
