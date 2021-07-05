package com.manickchand.upcomingmovies.data.di

import com.manickchand.upcomingmovies.data.repository.UpcomingMoviesRepository
import com.manickchand.upcomingmovies.data.repository.UpcomingMoviesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UpcomingMoviesRepository> { UpcomingMoviesRepositoryImpl(get(), get()) }
}