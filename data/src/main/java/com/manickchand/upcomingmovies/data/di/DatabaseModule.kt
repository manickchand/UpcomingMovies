package com.manickchand.upcomingmovies.data.di

import androidx.room.Room
import com.manickchand.upcomingmovies.data.repository.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "MOVIES"
        ).build()
    }

    single {
        get<AppDatabase>().movieDAO()
    }
}