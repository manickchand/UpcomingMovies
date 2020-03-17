package com.manickchand.upcomingmovies.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.models.Upcoming
import com.manickchand.upcomingmovies.repository.RetrofitInit
import com.manickchand.upcomingmovies.utils.EN_US
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.TOKEN_API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : BaseViewModel() {

    val moviesLiveData = MutableLiveData< Pair< List<Movie>?, Int> >()
    val hasErrorLiveData = MutableLiveData<Boolean>()

    fun getUpcomingList(page:Int){

        loading.value = true

        RetrofitInit.service.getUpcomingList(TOKEN_API, EN_US , page).enqueue(object:
            Callback<Upcoming> {

            override fun onFailure(call: Call<Upcoming>, t: Throwable) {
                Log.e(TAG_DEBUC,"[Error getUpcomingList] "+t.message)
                hasErrorLiveData.value = true
                loading.value = false
            }
            override fun onResponse(
                call: Call<Upcoming>,
                response: Response<Upcoming>
            ) {

                loading.value = false
                if(response.isSuccessful){
                    hasErrorLiveData.value = false
                    moviesLiveData.value = Pair(response.body()?.results ?: emptyList() , response.body()?.total_pages ?: 0)
                }else{
                    Log.e(TAG_DEBUC,"[Response getUpcomingList Error] code: "+response.code())
                    hasErrorLiveData.value = true
                }
            }
        })
    }

//    fun getSliderAnimes(){
//
//        animeSliderLiveData.value = listOf(
//            AnimeSlider(R.drawable.naruto, R.string.naruto),
//            AnimeSlider(R.drawable.berserk, R.string.berserk),
//            AnimeSlider(R.drawable.bleach, R.string.bleach),
//            AnimeSlider(R.drawable.dragonball, R.string.dragonball)
//        )
//    }
}