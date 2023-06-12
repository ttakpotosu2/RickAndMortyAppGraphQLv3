package com.example.rickandmortyappgraphqlv3.data.local

import androidx.room.TypeConverter
import com.example.rickandmortyappgraphqlv3.data.dtos.characters_dtos.Origin
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity
import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SourceTypeConverter {

    @TypeConverter
    fun fromString(source: String): List<String>{
        return Json.decodeFromString(source)
    }

    @TypeConverter
    fun toString(list: List<String>): String{
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromOrigin(source: Origin): String{
        return Json.encodeToString(source)
    }

    @TypeConverter
    fun toOrigin(storeOrigin: String): Origin{
        return  Json.decodeFromString(storeOrigin)
    }

    @TypeConverter
    fun fromInt(source: String): List<Int>{
        return Json.decodeFromString(source)
    }

    @TypeConverter
    fun toInt(source: List<Int>): String{
        return Json.encodeToString(source)
    }

    @TypeConverter
    fun toCharacterEntity(source: String): List<CharacterResultsEntity>{
        return source.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun fromCharacterEntity(source: List<CharacterResultsEntity>): String{
        return source.toString()
    }
}