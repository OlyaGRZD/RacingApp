package com.example.racingapp.data.mapper

import com.example.racingapp.data.db.RaceResultEntity
import com.example.racingapp.data.model.RaceResult

fun RaceResult.toEntity() = RaceResultEntity(
    winner = winner,
    timestamp = timestamp
)

fun RaceResultEntity.toModel() = RaceResult(
    winner = winner,
    timestamp = timestamp
)
