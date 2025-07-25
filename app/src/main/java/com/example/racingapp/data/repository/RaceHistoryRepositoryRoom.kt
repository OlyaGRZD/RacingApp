package com.example.racingapp.data.repository

import android.util.Log
import com.example.racingapp.data.db.RaceResultDao
import com.example.racingapp.data.mapper.toEntity
import com.example.racingapp.data.mapper.toModel
import com.example.racingapp.data.model.RaceResult
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class RaceHistoryRepositoryRoom(
    private val dao: RaceResultDao
) : RaceHistoryRepository {

    private val disposables = CompositeDisposable()

    override fun addResult(result: RaceResult) {
        val disposable = Completable.fromAction {
            dao.insert(result.toEntity())
        }
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {},
                onError = { throwable ->
                    Log.e("RoomInsert", "Error inserting result", throwable)
                }
            )

        disposables.add(disposable)
    }



    override fun getHistory(): Flowable<List<RaceResult>> {
        return dao.getAllResults()
            .map { list -> list.map { it.toModel() } }
    }

    override fun clear() {
        dao.clear()
    }
}
