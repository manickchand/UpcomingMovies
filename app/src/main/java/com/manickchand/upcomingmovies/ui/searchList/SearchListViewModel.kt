package com.manickchand.upcomingmovies.ui.searchList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.useCase.GenresUseCase
import com.manickchand.upcomingmovies.domain.useCase.MoviesUseCase
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import kotlinx.coroutines.launch

class SearchListViewModel(private val moviesUseCase: MoviesUseCase, private val genresUseCase: GenresUseCase) : BaseViewModel() {

    val searchListLiveData = MutableLiveData< Pair< List<Movie>?, Int> >()

    fun searchMovies(query:String, page:Int){

        launch {
            loading.value = true

            try {
                hasErrorLiveData.value = false
                searchListLiveData.value = moviesUseCase.searchMovies(query, page)
            }catch (t:Throwable){
                hasErrorLiveData.value = true
                Log.e(TAG_DEBUC,"[Error searchMovies] "+t.message)
            }finally {
                loading.value = false
            }
        }

    }

    fun getByGenre(id:Int, page:Int){

        launch {
            loading.value = true

            try {
                hasErrorLiveData.value = false
                searchListLiveData.value =  genresUseCase.getByGenre(id, page)
            }catch (t:Throwable){
                hasErrorLiveData.value = true
                Log.e(TAG_DEBUC,"[Error searchMovies] "+t.message)
            }finally {
                loading.value = false
            }
        }
    }
}