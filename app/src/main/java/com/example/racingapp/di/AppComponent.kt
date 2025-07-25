package com.example.racingapp.di

import android.app.Application
import com.example.racingapp.presentation.viewmodel.HistoryViewModel
import com.example.racingapp.presentation.viewmodel.RaceViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun provideRaceViewModelFactory(): RaceViewModel.Factory
    fun provideHistoryViewModelFactory(): HistoryViewModel.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}
