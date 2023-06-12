package com.example.rickandmortyappgraphqlv3.data.dtos.locations_dtos

import android.net.Uri
import com.example.rickandmortyappgraphqlv3.domain.model.LocationResultEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

//@Serializable
//data class LocationDto(
//    val created: String,
//    val dimension: String,
//    val id: Int,
//    @SerializedName("name") val locationName: String,
//    val residents: List<String>, //link from here to CharacterDto
//    val type: String,
//    @SerializedName("url") val locationUrl: String
//) {
//    fun toLocationEntity(): LocationResultEntity {
//        return LocationResultEntity(
//            created,
//            dimension,
//            id,
//            locationName,
//            residents.mapNotNull { Uri.parse(it).lastPathSegment },
//            type,
//            locationUrl
//        )
//    }
//}