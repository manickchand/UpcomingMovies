package com.manickchand.upcomingmovies.data.di

import com.manickchand.upcomingmovies.data.repository.GenresDataRepository
import com.manickchand.upcomingmovies.data.repository.MoviesDataRepository
import com.manickchand.upcomingmovies.domain.repository.GenresRepository
import com.manickchand.upcomingmovies.domain.repository.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<GenresRepository> { GenresDataRepository(get()) }

    factory<MoviesRepository> { MoviesDataRepository(get(), get()) }
}