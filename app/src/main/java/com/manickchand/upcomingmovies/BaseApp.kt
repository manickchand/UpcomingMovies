package com.manickchand.upcomingmovies

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.manickchand.upcomingmovies.di.appComponent
import com.manickchand.upcomingmovies.repository.AppDatabase
import com.manickchand.upcomingmovies.utils.DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp: Application(){

    override fun onCreate() {
        super.onCreate()
        configureDI()
        mContext = applicationContext
    }

    private fun configureDI() = startKoin {
        androidContext(this@BaseApp)
        modules(appComponent)
    }

    companion object{
        lateinit var mContext:Context

        fun getAppContext() = mContext

        fun getDB() = Room.databaseBuilder(
            mContext,
            AppDatabase::class.java, DB_NAME
        )
            .allowMainThreadQueries()
            .build()

    }
}