package com.manickchand.upcomingmovies.domain.useCase

import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.repository.MoviesRepository

class MoviesUseCase(private val repository: MoviesRepository)  {

    suspend fun getUpcomingList(page: Int) = repository.getUpcomingList(page)

    suspend fun getAllFromDB() = repository.getAllFromDB()

    suspend fun getMovieDetail(movie_id: Int) = repository.getMovieDetail(movie_id)

    suspend fun insertMovieInDB(movie: Movie) = repository.insertMovieInDB(movie)

    suspend fun getMovieByIdDB(movie_id: Int) = repository.getMovieByIdDB(movie_id)

    suspend fun searchMovies(query: String, page: Int) = repository.searchMovies(query, page)

}