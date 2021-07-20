package com.manickchand.upcomingmovies.ui.movieDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.useCase.MoviesUseCase
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.ViewState
import com.manickchand.upcomingmovies.utils.toImmutable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(private val moviesUseCase: MoviesUseCase) : BaseViewModel() {

    private val movieDetailLiveData = MutableLiveData<ViewState<Movie>>()
    fun getMovieDetail() = movieDetailLiveData.toImmutable()

    fun getMovieDetail(movie_id: Int) {

        movieDetailLiveData.value = ViewState.Loading

        launch {
            try {
                movieDetailLiveData.value =
                    ViewState.Success(moviesUseCase.getMovieDetail(movie_id))
            } catch (t: Throwable) {
                movieDetailLiveData.value = ViewState.Failed(t)
                Log.i(TAG_DEBUC, "[error] getMovieDetail: ${t.message}")

            }
        }
    }

    fun insertMovie(movie: Movie) {
        launch {
            withContext(IO) {
                moviesUseCase.insertMovieInDB(movie)
            }
        }
    }

    fun getMovie(movie_id: Int) {
        movieDetailLiveData.value = ViewState.Loading
        launch {
            withContext(IO) {
                try {
                    movieDetailLiveData.postValue(
                        ViewState.Success(
                            moviesUseCase.getMovieByIdDB(
                                movie_id
                            )
                        )
                    )
                } catch (t: Throwable) {
                    movieDetailLiveData.value = ViewState.Failed(t)
                    Log.i(TAG_DEBUC, "[error] getMovie: ${t.message}")
                }
            }
        }
    }

}