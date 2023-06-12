package com.example.rickandmortyappgraphqlv3.di

import android.content.Context
import androidx.room.Room
import com.example.rickandmortyappgraphqlv3.data.local.RickAndMortyAppResultsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideEpisodeDatabase(
        @ApplicationContext context: Context
    ): RickAndMortyAppResultsDatabase{
        return Room.databaseBuilder(
            context,
            RickAndMortyAppResultsDatabase::class.java,
            "rick_and_morty_project_database"
        ).build()
    }
}