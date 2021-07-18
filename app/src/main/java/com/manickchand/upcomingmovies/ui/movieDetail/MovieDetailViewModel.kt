package com.manickchand.upcomingmovies.ui.movieDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.useCase.MoviesUseCase
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(private val moviesUseCase: MoviesUseCase) : BaseViewModel(){

    private val _movieDetailLiveData = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = Transformations.map(_movieDetailLiveData) { it }
    val load: LiveData<Boolean> = Transformations.map(loading) { it }

    fun getMovieDetail(movie_id:Int){

        launch {
            loading.value = true

            try {

                _movieDetailLiveData.value = moviesUseCase.getMovieDetail( movie_id )
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
                moviesUseCase.insertMovieInDB(movie)
            }
        }
    }

    fun getMovie(movie_id:Int){
        launch {
            withContext(IO) {
                try {
                    _movieDetailLiveData.postValue(moviesUseCase.getMovieByIdDB(movie_id))
                }catch (t:Throwable){
                    Log.i(TAG_DEBUC, "[error] getMovie: ${t.message}")
                }
            }
        }
    }

}