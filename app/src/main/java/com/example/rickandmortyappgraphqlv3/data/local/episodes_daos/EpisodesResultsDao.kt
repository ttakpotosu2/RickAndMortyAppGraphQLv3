package com.example.rickandmortyappgraphqlv3.data.local.episodes_daos

import androidx.paging.PagingSource
import androidx.room.*
import com.example.rickandmortyappgraphqlv3.domain.model.EpisodesResultsEntity

@Dao
interface EpisodesResultsDao {

    @Query("SELECT * FROM episodes_results_table")
    fun getEpisodes(): PagingSource<Int, EpisodesResultsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEpisodes(episodes: List<EpisodesResultsEntity>)

    // update table with new episodes
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEpisodes(updateIds: List<EpisodesResultsEntity>)

    @Query("DELETE FROM episodes_results_table")
    suspend fun deleteEpisodes()

    @Query("SELECT * FROM episodes_results_table WHERE id = :episode")
    suspend fun getSingleEpisode(episode: String): EpisodesResultsEntity

    @Query("SELECT * FROM episodes_results_table WHERE id IN (:ids)")
    suspend fun getEpisodeForCharacter (ids: List<String>): List<EpisodesResultsEntity>
}