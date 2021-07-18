package com.manickchand.upcomingmovies.domain.repository

import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.domain.models.Movie

interface GenresRepository {
    suspend fun getAllGenres():List<Genre>
    suspend fun getByGenre(id:Int, page:Int): Pair< List<Movie>?, Int>
}