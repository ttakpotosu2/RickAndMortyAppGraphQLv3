package com.example.rickandmortyappgraphqlv3.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations_result_table")
data class LocationResultEntity(
    @PrimaryKey val id: String,
    val name: String,
    val dimension: String,
    val type: String,
    val residents: List<String>
)