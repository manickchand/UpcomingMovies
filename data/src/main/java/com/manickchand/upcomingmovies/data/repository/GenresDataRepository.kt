package com.manickchand.upcomingmovies.data.repository

import com.manickchand.upcomingmovies.data.remote.IServiceRetrofit
import com.manickchand.upcomingmovies.domain.repository.GenresRepository

class GenresDataRepository(private val service: IServiceRetrofit) :
    GenresRepository {

    override suspend fun getAllGenres() = service.getAllGenres().genres

    override suspend fun getByGenre(id: Int, page: Int) = service.getByGenre(id, page)

}