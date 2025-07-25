package com.example.racingapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RaceResultEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun raceResultDao(): RaceResultDao
}
