package com.manickchand.upcomingmovies.ui.searchList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.repository.IServiceRetrofit
import com.manickchand.upcomingmovies.utils.EN_US
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.TOKEN_API
import kotlinx.coroutines.launch

class SearchListViewModel(private val service: IServiceRetrofit) : BaseViewModel() {

    val searchListLiveData = MutableLiveData< Pair< List<Movie>?, Int> >()

    fun searchMovies(query:String, page:Int){

        launch {
            loading.value = true

            try {
                val response = service.searchMovies(TOKEN_API, EN_US, query, page)
                hasErrorLiveData.value = false
                searchListLiveData.value = Pair( response.results ?: emptyList(), response.total_pages ?: 0)
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
                val response = service.getByGenre(TOKEN_API, EN_US, id, page)
                hasErrorLiveData.value = false
                searchListLiveData.value = Pair( response.results ?: emptyList(), response.total_pages ?: 0)
            }catch (t:Throwable){
                hasErrorLiveData.value = true
                Log.e(TAG_DEBUC,"[Error searchMovies] "+t.message)
            }finally {
                loading.value = false
            }
        }
    }
}