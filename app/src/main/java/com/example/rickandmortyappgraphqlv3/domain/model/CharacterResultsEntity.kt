package com.example.rickandmortyappgraphqlv3.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmortyappgraphqlv3.data.dtos.characters_dtos.Origin

@Entity(tableName = "characters_results_table")
data class CharacterResultsEntity(
    @PrimaryKey val id: String,
    val episodes: List<String>,
    val gender: String,
    val image: String,
    val name: String,
 //   val charactersUrl: String,
    val status: String,
 //   val origin: Origin,
    val species: String
)