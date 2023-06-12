package com.example.rickandmortyappgraphqlv3.presentation.screens.states

import com.example.rickandmortyappgraphqlv3.domain.repository.CharacterAndEpisodes

sealed class CharacterState {
    object Loading: CharacterState()
    data class Success(val character: CharacterAndEpisodes): CharacterState()
    class Error(val message: String? = null) : CharacterState()
}