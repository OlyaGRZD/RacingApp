package com.example.racingapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "race_results")
data class RaceResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val winner: String,
    val timestamp: Long
)
