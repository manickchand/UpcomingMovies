package com.manickchand.upcomingmovies.domain.di

import com.manickchand.upcomingmovies.domain.useCase.GenresUseCase
import com.manickchand.upcomingmovies.domain.useCase.MoviesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        MoviesUseCase(get())
    }

    factory {
        GenresUseCase(get())
    }
}