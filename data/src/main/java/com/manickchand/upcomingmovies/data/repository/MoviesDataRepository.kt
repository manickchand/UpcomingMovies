package com.manickchand.upcomingmovies.data.repository

import com.manickchand.upcomingmovies.data.local.MovieDAO
import com.manickchand.upcomingmovies.data.remote.IServiceRetrofit
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.models.Upcoming
import com.manickchand.upcomingmovies.domain.repository.MoviesRepository


class MoviesDataRepository(private val service: IServiceRetrofit, private val database: MovieDAO) :
    MoviesRepository {

    override suspend fun getUpcomingList(page: Int) = service.getUpcomingList(page)

    override suspend fun getAllFromDB() = Upcoming(total_pages = 1, results = database.getAll())

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
