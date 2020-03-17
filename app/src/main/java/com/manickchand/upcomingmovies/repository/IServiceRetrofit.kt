package com.manickchand.upcomingmovies.repository

import com.manickchand.upcomingmovies.models.Upcoming
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IServiceRetrofit {


    @GET("movie/upcoming")
    fun getUpcomingList( @Query("api_key") api_key:String,
                         @Query("language") language:String,
                         @Query("page") page:Int): Call<Upcoming>

//    @GET("anime/{anime_id}")
//    fun getAnimeById(@Path("anime_id") anime_id:Int): Call<Movie>

//    @GET("season/{year}/{season}")
//    fun getAnimesBySeason(@Path("year") year:Int, @Path("season") season:String): Call<SeasonResponse>

}