package com.example.racingapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.racingapp.data.model.RaceResult
import com.example.racingapp.data.repository.RaceHistoryRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import jakarta.inject.Inject


class HistoryViewModel(
    private val historyRepository: RaceHistoryRepository
) : ViewModel() {

    val history = mutableStateListOf<RaceResult>()
    private val disposables = CompositeDisposable()

    init {
        disposables.add(
            historyRepository.getHistory()
                .subscribeBy { list ->
                    history.clear()
                    history.addAll(list)
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    class Factory @Inject constructor(
        private val historyRepository: RaceHistoryRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HistoryViewModel(historyRepository) as T
        }
    }
}