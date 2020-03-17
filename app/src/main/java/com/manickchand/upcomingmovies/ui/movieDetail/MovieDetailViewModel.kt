package com.manickchand.upcomingmovies.ui.movieDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.repository.RetrofitInit
import com.manickchand.upcomingmovies.utils.EN_US
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.TOKEN_API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : BaseViewModel(){

    val _movieDetailLiveData = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = Transformations.map(_movieDetailLiveData) { it }
    val load: LiveData<Boolean> = Transformations.map(loading) { it }

    fun getMovieDetail(movie_id:Int){

        loading.value = true

        RetrofitInit.service.getMovieDetail( movie_id, TOKEN_API, EN_US).enqueue(object:
            Callback<Movie> {

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(TAG_DEBUC,"[Error getMovieDetail] "+t.message)
                loading.value = false
            }
            override fun onResponse(
                call: Call<Movie>,
                response: Response<Movie>
            ) {
                loading.value = false
                if(response.isSuccessful){
                    _movieDetailLiveData.value = response.body() ?: null
                }else{
                    Log.e(TAG_DEBUC,"[Response getMovieDetail Error] code: "+response.code())
                }
            }
        })
    }
}