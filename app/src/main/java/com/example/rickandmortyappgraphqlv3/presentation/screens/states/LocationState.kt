package com.example.rickandmortyappgraphqlv3.presentation.screens.states

import com.example.rickandmortyappgraphqlv3.domain.repository.LocationAndCharacters

sealed class LocationState {
    object Loading: LocationState()
    data class Success(val location: LocationAndCharacters): LocationState()
    class Error(val message: String? = null) : LocationState()
}
