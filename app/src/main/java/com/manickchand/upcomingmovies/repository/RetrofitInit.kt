package com.manickchand.upcomingmovies.repository

import com.manickchand.upcomingmovies.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInit {

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: IServiceRetrofit = initRetrofit().create(IServiceRetrofit::class.java)
}