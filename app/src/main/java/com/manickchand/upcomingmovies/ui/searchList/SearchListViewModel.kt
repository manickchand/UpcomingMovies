package com.manickchand.upcomingmovies.ui.searchList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.data.models.Movie
import com.manickchand.upcomingmovies.data.repository.UpcomingMoviesRepository
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import kotlinx.coroutines.launch

class SearchListViewModel(private val upcomingMoviesRepository: UpcomingMoviesRepository) : BaseViewModel() {

    val searchListLiveData = MutableLiveData< Pair< List<Movie>?, Int> >()

    fun searchMovies(query:String, page:Int){

        launch {
            loading.value = true

            try {
                hasErrorLiveData.value = false
                searchListLiveData.value = upcomingMoviesRepository.searchMovies(query, page)
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
                searchListLiveData.value =  upcomingMoviesRepository.getByGenre(id, page)
            }catch (t:Throwable){
                hasErrorLiveData.value = true
                Log.e(TAG_DEBUC,"[Error searchMovies] "+t.message)
            }finally {
                loading.value = false
            }
        }
    }
}