package com.example.racingapp.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf

data class RaceHorse(
    val id: Int,
    val name: String,
    val imageResId: Int,
    val progress: MutableState<Float> = mutableFloatStateOf(0f)
)

