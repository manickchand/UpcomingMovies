package com.manickchand.upcomingmovies.data.repository

import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.repository.GenresRepository

class GenresDataRepository(private val service: IServiceRetrofit) :
    GenresRepository {

    override suspend fun getAllGenres(): List<Genre> {
        return service.getAllGenres().genres
    }

    override suspend fun getByGenre(id: Int, page: Int): Pair<List<Movie>?, Int> {
        val response = service.getByGenre(id, page)
        return Pair(response.results ?: emptyList(), response.total_pages ?: 0)
    }

}