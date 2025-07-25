package com.example.racingapp.data.model

data class RaceResult(
    val winner: String,
    val timestamp: Long = System.currentTimeMillis()
)