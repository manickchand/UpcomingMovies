package com.manickchand.upcomingmovies.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Genre
import com.manickchand.upcomingmovies.repository.IServiceRetrofit
import com.manickchand.upcomingmovies.utils.EN_US
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.TOKEN_API
import kotlinx.coroutines.launch

class SearchViewModel(private val service: IServiceRetrofit) : BaseViewModel() {

    val genreListLiveData = MutableLiveData< List<Genre> >()

    fun getAllGenres(){

        launch {
            loading.value = true

            try {
                val response = service.getAllGenres(TOKEN_API, EN_US)
                hasErrorLiveData.value = false
                genreListLiveData.value = response.genres ?: emptyList()
            }catch (t:Throwable){
                hasErrorLiveData.value = true
                Log.e(TAG_DEBUC,"[Error getAllGenres] "+t.message)
            }finally {
                loading.value = false
            }
        }
    }
}