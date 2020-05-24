package com.manickchand.upcomingmovies.di

import com.manickchand.upcomingmovies.repository.UpcomingMoviesRepository
import com.manickchand.upcomingmovies.repository.UpcomingMoviesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UpcomingMoviesRepository> { UpcomingMoviesRepositoryImpl(get(), get()) }
}