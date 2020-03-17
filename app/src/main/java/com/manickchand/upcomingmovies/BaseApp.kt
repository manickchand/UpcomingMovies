package com.manickchand.upcomingmovies

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.manickchand.upcomingmovies.repository.AppDatabase
import com.manickchand.upcomingmovies.utils.DB_NAME

class BaseApp: Application(){

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
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