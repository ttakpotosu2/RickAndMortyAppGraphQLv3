package com.example.rickandmortyappgraphqlv3.data.remote

import com.example.rickandmortyappgraphqlv3.data.PagedData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//interface RickAndMortyApi {
//
//    @GET("character/")
//    suspend fun getAllCharacters(@Query("page") page: String): PagedData<CharacterDto>
//
//    @GET("location")
//    suspend fun getAllLocations(@Query("page") page: String): PagedData<LocationDto>
//
//    @GET("episode")
//    suspend fun getAllEpisodes(@Query("page") page: String): PagedData<EpisodeDto>
//
//    @GET("episode/{episode}")
//    suspend fun getMultipleEpisodes(@Path("episode") episode: String): List<EpisodeDto>
//
//    @GET("character/{character}")
//    suspend fun getMultipleCharacters(@Path("character") character: String): List<CharacterDto>
//}