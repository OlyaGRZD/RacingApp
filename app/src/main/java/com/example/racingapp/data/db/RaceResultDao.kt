package com.example.racingapp.data.db

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface RaceResultDao {

    @Query("SELECT * FROM race_results ORDER BY timestamp DESC")
    fun getAllResults(): Flowable<List<RaceResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(result: RaceResultEntity)

    @Query("DELETE FROM race_results")
    fun clear()
}
