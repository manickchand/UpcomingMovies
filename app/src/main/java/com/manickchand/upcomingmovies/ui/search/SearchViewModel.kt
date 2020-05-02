package com.manickchand.upcomingmovies.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Genre
import com.manickchand.upcomingmovies.models.GenreList
import com.manickchand.upcomingmovies.repository.IServiceRetrofit
import com.manickchand.upcomingmovies.utils.EN_US
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.TOKEN_API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val service: IServiceRetrofit) : BaseViewModel() {

    val genreListLiveData = MutableLiveData< List<Genre> >()

    fun getAllGenres(){

        loading.value = true

        service.getAllGenres(TOKEN_API, EN_US).enqueue(object:
            Callback<GenreList> {

            override fun onFailure(call: Call<GenreList>, t: Throwable) {
                Log.e(TAG_DEBUC,"[Error getAllGenres] "+t.message)
                hasErrorLiveData.value = true
                loading.value = false
            }
            override fun onResponse(
                call: Call<GenreList>,
                response: Response<GenreList>
            ) {
                loading.value = false
                if(response.isSuccessful){
                    hasErrorLiveData.value = false
                    genreListLiveData.value = response.body()?.genres ?: emptyList()
                }else{
                    hasErrorLiveData.value = true
                    Log.e(TAG_DEBUC,"[Response getAllGenres Error] code: "+response.code())
                }
            }
        })
    }
}