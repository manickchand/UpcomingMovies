package com.manickchand.upcomingmovies.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manickchand.upcomingmovies.models.Movie

@Database(entities = [Movie::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
}
