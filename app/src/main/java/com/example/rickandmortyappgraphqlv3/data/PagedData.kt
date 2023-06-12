package com.example.rickandmortyappgraphqlv3.data

import com.example.rickandmortyappgraphqlv3.data.dtos.locations_dtos.Info

data class PagedData<T>(
    val info: Info,
    val results: List<T>
)
