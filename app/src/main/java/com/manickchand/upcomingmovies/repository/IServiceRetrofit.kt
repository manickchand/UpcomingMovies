package com.manickchand.upcomingmovies.repository

import com.manickchand.upcomingmovies.models.GenreList
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

    @GET("genre/movie/list")
    fun getAllGenres( @Query("api_key") api_key:String,
                           @Query("language") language:String): Call<GenreList>

    @GET("search/movie")
    fun searchMovies( @Query("api_key") api_key:String,
                      @Query("language") language:String,
                      @Query("query") query:String,
                      @Query("page") page:Int): Call<Upcoming>

    @GET("discover/movie")
    fun getByGenre( @Query("api_key") api_key:String,
                      @Query("language") language:String,
                      @Query("with_genres") with_genres:Int,
                      @Query("page") page:Int): Call<Upcoming>

}