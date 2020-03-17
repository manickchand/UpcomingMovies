package com.manickchand.upcomingmovies.repository

import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.models.Upcoming
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IServiceRetrofit {


    @GET("movie/upcoming")
    fun getUpcomingList( @Query("api_key") api_key:String,
                         @Query("language") language:String,
                         @Query("page") page:Int): Call<Upcoming>

    @GET("movie/{movie_id}")
    fun getMovieDetail( @Path("movie_id") movie_id:Int,
                        @Query("api_key") api_key:String,
                        @Query("language") language:String): Call<Movie>

//    @GET("season/{year}/{season}")
//    fun getAnimesBySeason(@Path("year") year:Int, @Path("season") season:String): Call<SeasonResponse>

}