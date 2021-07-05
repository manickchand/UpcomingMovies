package com.manickchand.upcomingmovies.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manickchand.upcomingmovies.data.models.Movie

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun findById(id: Int): Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movie: Movie)

}