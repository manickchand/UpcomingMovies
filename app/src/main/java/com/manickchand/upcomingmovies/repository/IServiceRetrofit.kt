package com.manickchand.upcomingmovies.repository

import com.manickchand.upcomingmovies.models.GenreList
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.models.Upcoming
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IServiceRetrofit {

    @GET("movie/upcoming")
    suspend fun getUpcomingList( @Query("api_key") api_key:String,
                         @Query("language") language:String,
                         @Query("page") page:Int): Upcoming

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail( @Path("movie_id") movie_id:Int,
                        @Query("api_key") api_key:String,
                        @Query("language") language:String): Movie

    @GET("genre/movie/list")
    suspend fun getAllGenres( @Query("api_key") api_key:String,
                           @Query("language") language:String): GenreList

    @GET("search/movie")
    suspend fun searchMovies( @Query("api_key") api_key:String,
                      @Query("language") language:String,
                      @Query("query") query:String,
                      @Query("page") page:Int): Upcoming

    @GET("discover/movie")
    suspend fun getByGenre( @Query("api_key") api_key:String,
                      @Query("language") language:String,
                      @Query("with_genres") with_genres:Int,
                      @Query("page") page:Int): Upcoming

}