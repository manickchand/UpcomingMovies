package com.manickchand.upcomingmovies.domain.repository

import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.models.Upcoming

interface MoviesRepository {
    suspend fun getUpcomingList(page: Int): Upcoming
    suspend fun getAllFromDB(): Upcoming
    suspend fun getMovieDetail( movie_id:Int ): Movie
    suspend fun insertMovieInDB(movie: Movie)
    suspend fun getMovieByIdDB(movie_id: Int): Movie
    suspend fun searchMovies(query:String, page:Int): Pair< List<Movie>?, Int>
}