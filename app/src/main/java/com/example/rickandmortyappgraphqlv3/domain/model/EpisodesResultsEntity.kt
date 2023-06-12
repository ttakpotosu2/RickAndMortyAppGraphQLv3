package com.example.rickandmortyappgraphqlv3.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episodes_results_table")
data class EpisodesResultsEntity(
    @PrimaryKey val id: String,
    val name: String,
    val episode: String,
    val airDate: String,
    val characters: List<String>,
)
