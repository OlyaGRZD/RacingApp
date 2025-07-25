package com.example.racingapp.di

import android.app.Application
import androidx.room.Room
import com.example.racingapp.data.db.AppDatabase
import com.example.racingapp.data.db.RaceResultDao
import com.example.racingapp.data.repository.RaceHistoryRepository
import com.example.racingapp.data.repository.RaceHistoryRepositoryRoom
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "race_db").build()

    @Provides
    @Singleton
    fun provideRaceResultDao(db: AppDatabase): RaceResultDao = db.raceResultDao()

    @Provides
    @Singleton
    fun provideRaceHistoryRepository(dao: RaceResultDao): RaceHistoryRepository =
        RaceHistoryRepositoryRoom(dao)
}
