package com.example.racingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.racingapp.data.model.RaceHorse
import com.example.racingapp.data.model.RaceResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelProvider
import com.example.racingapp.R
import com.example.racingapp.data.repository.RaceHistoryRepository
import jakarta.inject.Inject

class RaceViewModel(
    private val historyRepository: RaceHistoryRepository
) : ViewModel() {

    val horses = mutableStateListOf<RaceHorse>()
    val winner = mutableStateOf<String?>(null)
    val countdown = mutableStateOf<Int?>(null)
    val isRaceRunning = mutableStateOf(false)

    private val disposables = CompositeDisposable()

    init {
        resetRace()
    }

    fun resetRace() {
        winner.value = null
        countdown.value = null
        isRaceRunning.value = false
        horses.clear()

        val horseImages = listOf(
            R.drawable.horse1,
            R.drawable.horse2,
            R.drawable.horse3,
            R.drawable.horse4,
            R.drawable.horse5
        )

        val horseNames = listOf("Бетси", "Дуплет", "Коламбус", "Джингл", "Томми")

        for (i in horseImages.indices) {
            horses.add(
                RaceHorse(
                    id = i + 1,
                    name = horseNames[i],
                    imageResId = horseImages[i],
                    progress = mutableStateOf(0f)
                )
            )
        }
    }

    fun startRace() {
        resetRace()
        isRaceRunning.value = true
        countdown.value = 3

        Observable.interval(1, TimeUnit.SECONDS)
            .take(4)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tick ->
                val count = 3 - tick.toInt()
                countdown.value = if (count >= 0) count else null

                if (tick == 3L) {
                    launchRace()
                }
            }
            .let { disposables.add(it) }
    }

    private fun launchRace() {
        countdown.value = null

        val raceObservable = Observable.interval(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .takeWhile { winner.value == null }
            .doOnNext {
                for (horse in horses) {
                    if (winner.value == null) {
                        val step = Random.nextFloat() * 0.05f
                        val newProgress = (horse.progress.value + step).coerceAtMost(1f)
                        horse.progress.value = newProgress

                        if (newProgress >= 1f) {
                            winner.value = horse.name
                            isRaceRunning.value = false
                            historyRepository.addResult(RaceResult(winner = horse.name))
                        }
                    }
                }
            }

        disposables.add(raceObservable.subscribe())
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    class Factory @Inject constructor(
        private val historyRepository: RaceHistoryRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RaceViewModel(historyRepository) as T
        }
    }
}
