package com.manickchand.upcomingmovies.data.di

import com.manickchand.upcomingmovies.data.BuildConfig
import com.manickchand.upcomingmovies.data.repository.IServiceRetrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create<IServiceRetrofit>()
    }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor { chain -> addRequestParameters(chain) }
        .build()
}

fun addRequestParameters(chain: Interceptor.Chain): Response {
    var request = chain.request()
    val builder = request.url().newBuilder()

    builder
        .addQueryParameter("api_key", BuildConfig.TOKEN_API)
        .addQueryParameter("language", BuildConfig.EN_US)

    return chain.proceed(request.newBuilder().url(builder.build()).build())
}