package com.manickchand.upcomingmovies.data.repository

import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.repository.MoviesRepository


class MoviesDataRepository(private val service: IServiceRetrofit, private val database: MovieDAO) :
    MoviesRepository {

    override suspend fun getUpcomingList(page: Int): Pair<List<Movie>?, Int> {
        val result = service.getUpcomingList(page)
        return Pair(result.results ?: emptyList(), result.total_pages ?: 0)
    }

    override suspend fun getAllFromDB(): Pair<List<Movie>, Int> {
        return try {
            Pair(database.getAll(), 1)
        } catch (t: Throwable) {
            Pair(emptyList(), 1)
        }
    }

    override suspend fun getMovieDetail(movie_id: Int): Movie {
        return service.getMovieDetail(movie_id)
    }

    override suspend fun insertMovieInDB(movie: Movie) {
        database.insertAll(movie)
    }

    override suspend fun getMovieByIdDB(movie_id: Int): Movie {
        return database.findById(movie_id)
    }

    override suspend fun searchMovies(query: String, page: Int): Pair<List<Movie>?, Int> {
        val response = service.searchMovies(query, page)
        return Pair(response.results ?: emptyList(), response.total_pages ?: 0)
    }

}
