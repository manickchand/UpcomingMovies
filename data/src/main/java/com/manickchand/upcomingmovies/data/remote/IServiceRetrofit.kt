package com.manickchand.upcomingmovies.data.remote

import com.manickchand.upcomingmovies.domain.models.GenreList
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.models.Upcoming
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IServiceRetrofit {

    @GET("movie/upcoming")
    suspend fun getUpcomingList(@Query("page") page: Int): Upcoming

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movie_id: Int): Movie

    @GET("genre/movie/list")
    suspend fun getAllGenres(): GenreList

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Upcoming

    @GET("discover/movie")
    suspend fun getByGenre(
        @Query("with_genres") with_genres: Int,
        @Query("page") page: Int
    ): Upcoming

}