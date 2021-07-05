package com.manickchand.upcomingmovies.data.repository

import com.manickchand.upcomingmovies.data.models.Genre
import com.manickchand.upcomingmovies.data.models.Movie

interface UpcomingMoviesRepository {

    suspend fun getUpcomingList(page: Int): Pair< List<Movie>?, Int>

    suspend fun getAllFromDB():Pair<List<Movie> , Int>

    suspend fun getMovieDetail( movie_id:Int ): Movie

    suspend fun insertMovieInDB(movie: Movie)

    suspend fun getMovieByIdDB(movie_id: Int):Movie

    suspend fun getAllGenres():List<Genre>

    suspend fun searchMovies(query:String, page:Int): Pair< List<Movie>?, Int>

    suspend fun getByGenre(id:Int, page:Int): Pair< List<Movie>?, Int>
}

class UpcomingMoviesRepositoryImpl(private val service: IServiceRetrofit, private val database: MovieDAO):UpcomingMoviesRepository{

    override suspend fun getUpcomingList(page:Int): Pair< List<Movie>?, Int> {
         val result = service.getUpcomingList( page )
        return Pair(result.results ?: emptyList() , result.total_pages ?: 0)
    }

    override suspend fun getAllFromDB(): Pair<List<Movie> , Int> {
        return try {
             Pair(database.getAll(),1)
        }catch (t:Throwable){
            Pair(emptyList(),1)
        }
    }

    override suspend fun getMovieDetail(movie_id: Int): Movie {
        return service.getMovieDetail(movie_id)
    }

    override suspend fun insertMovieInDB(movie: Movie) {
        database.insertAll(movie)
    }

    override suspend fun getMovieByIdDB(movie_id: Int): Movie {
        return database.findById(movie_id)
    }

    override suspend fun getAllGenres(): List<Genre> {
        return service.getAllGenres().genres
    }

    override suspend fun searchMovies(query: String, page: Int): Pair<List<Movie>?, Int> {
        val response = service.searchMovies( query, page )
        return Pair( response.results ?: emptyList(), response.total_pages ?: 0)
    }

    override suspend fun getByGenre(id: Int, page: Int): Pair<List<Movie>?, Int> {
        val response = service.getByGenre( id, page )
        return Pair( response.results ?: emptyList(), response.total_pages ?: 0)
    }

}