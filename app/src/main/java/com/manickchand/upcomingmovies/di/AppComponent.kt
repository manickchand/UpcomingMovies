package com.manickchand.upcomingmovies.di

import com.manickchand.upcomingmovies.data.di.*

val appComponent = listOf(
    viewModelModule,
    networkModule,
    databaseModule,
    repositoryModule
)