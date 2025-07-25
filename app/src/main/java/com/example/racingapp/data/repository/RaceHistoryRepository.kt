package com.example.racingapp.data.repository

import com.example.racingapp.data.model.RaceResult
import io.reactivex.Flowable

interface RaceHistoryRepository {
    fun addResult(result: RaceResult)
    fun getHistory(): Flowable<List<RaceResult>>
    fun clear()
}