package com.example.rickandmortyappgraphqlv3.data.local.characters_daos

import androidx.paging.PagingSource
import androidx.room.*
import com.example.rickandmortyappgraphqlv3.domain.model.CharacterResultsEntity

@Dao
interface CharactersResultsDao {

    @Query("SELECT * FROM characters_results_table")
    fun getCharacters(): PagingSource<Int, CharacterResultsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacters(characters: List<CharacterResultsEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCharacters(updateIds: List<CharacterResultsEntity>)

    @Query("DELETE FROM characters_results_table")
    suspend fun deleteCharacters()

    @Query("SELECT * FROM characters_results_table WHERE id = :id ")
    suspend fun getCharacterById(id: String): CharacterResultsEntity

    @Query("SELECT * FROM characters_results_table WHERE id IN (:id)")
    suspend fun getCharacterForEpisode (id: List<String>): List<CharacterResultsEntity>

    @Query("SELECT * FROM characters_results_table WHERE id IN (:id)")
    suspend fun getCharacterForLocation (id: List<String>): List<CharacterResultsEntity>
}