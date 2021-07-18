package com.manickchand.upcomingmovies.domain.useCase

import com.manickchand.upcomingmovies.domain.repository.GenresRepository

class GenresUseCase(private val repository: GenresRepository) {
    suspend fun getAllGenres() = repository.getAllGenres()
    suspend fun getByGenre(id:Int, page:Int) = repository.getByGenre(id, page)
}