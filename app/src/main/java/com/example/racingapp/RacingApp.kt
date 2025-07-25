package com.example.racingapp

import android.app.Application
import com.example.racingapp.di.AppComponent
import com.example.racingapp.di.DaggerAppComponent

class RacingApp : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
