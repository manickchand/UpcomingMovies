package com.manickchand.upcomingmovies.di

import androidx.room.Room
import com.manickchand.upcomingmovies.repository.AppDatabase
import com.manickchand.upcomingmovies.utils.DB_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    single {
        get<AppDatabase>().movieDAO()
    }
}