package com.manickchand.upcomingmovies.di

import com.manickchand.upcomingmovies.data.di.*
import com.manickchand.upcomingmovies.domain.di.domainModule

val appComponent = listOf(
    repositoryModule,
    domainModule,
    viewModelModule,
    networkModule,
    databaseModule

)