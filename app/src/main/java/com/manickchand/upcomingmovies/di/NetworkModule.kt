package com.manickchand.upcomingmovies.di

import com.manickchand.upcomingmovies.repository.IServiceRetrofit
import com.manickchand.upcomingmovies.utils.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create<IServiceRetrofit>()
    }
}