package com.manickchand.upcomingmovies.domain.repository

import com.manickchand.upcomingmovies.domain.models.Movie

interface MoviesRepository {
    suspend fun getUpcomingList(page: Int): Pair< List<Movie>?, Int>
    suspend fun getAllFromDB():Pair<List<Movie> , Int>
    suspend fun getMovieDetail( movie_id:Int ): Movie
    suspend fun insertMovieInDB(movie: Movie)
    suspend fun getMovieByIdDB(movie_id: Int): Movie
    suspend fun searchMovies(query:String, page:Int): Pair< List<Movie>?, Int>
}