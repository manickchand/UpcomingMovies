package com.manickchand.upcomingmovies

import android.app.Application
import com.manickchand.upcomingmovies.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDI()
    }

    private fun configureDI() = startKoin {
        androidContext(this@BaseApp)
        modules(appComponent)
    }
}