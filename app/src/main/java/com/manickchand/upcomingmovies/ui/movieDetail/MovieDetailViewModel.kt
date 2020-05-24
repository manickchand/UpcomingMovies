package com.manickchand.upcomingmovies.ui.movieDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.repository.IServiceRetrofit
import com.manickchand.upcomingmovies.repository.MovieDAO
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(private val service: IServiceRetrofit, private val database: MovieDAO) : BaseViewModel(){

    private val _movieDetailLiveData = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = Transformations.map(_movieDetailLiveData) { it }
    val load: LiveData<Boolean> = Transformations.map(loading) { it }

    fun getMovieDetail(movie_id:Int){

        launch {
            loading.value = true

            try {
                val result = service.getMovieDetail( movie_id )
                _movieDetailLiveData.value = result ?: null
                hasErrorLiveData.value = false

            }catch (t:Throwable){
                hasErrorLiveData.value = true
                Log.i(TAG_DEBUC, "[error] getMovieDetail: ${t.message}")

            }finally {
                loading.value = false
            }

        }
    }

    fun insertMovie(movie:Movie){
        launch {
            withContext(IO) {
                database.insertAll(movie)
            }
        }
    }

    fun getMovie(movie_id:Int){
        launch {
            var movie:Movie? = null
            withContext(IO) {
                try {
                    movie = database.findById(movie_id)
                }catch (t:Throwable){
                    Log.i(TAG_DEBUC, "[error] getMovie: ${t.message}")
                }
            }
            _movieDetailLiveData.value = movie
        }
    }
}