package com.manickchand.upcomingmovies.ui.searchList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.models.Upcoming
import com.manickchand.upcomingmovies.repository.IServiceRetrofit
import com.manickchand.upcomingmovies.utils.EN_US
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.TOKEN_API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchListViewModel(private val service: IServiceRetrofit) : BaseViewModel() {

    val searchListLiveData = MutableLiveData< Pair< List<Movie>?, Int> >()

    fun searchMovies(query:String, page:Int){

        loading.value = true

        service.searchMovies(TOKEN_API, EN_US, query, page).enqueue(object:
            Callback<Upcoming> {

            override fun onFailure(call: Call<Upcoming>, t: Throwable) {
                Log.e(TAG_DEBUC,"[Error searchMovies] "+t.message)
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
                    searchListLiveData.value = Pair( response.body()?.results ?: emptyList(), response.body()?.total_pages ?: 0)
                }else{
                    hasErrorLiveData.value = true
                    Log.e(TAG_DEBUC,"[Response searchMovies Error] code: "+response.code())
                }
            }
        })
    }

    fun getByGenre(id:Int, page:Int){

        loading.value = true

        service.getByGenre(TOKEN_API, EN_US, id, page).enqueue(object:
            Callback<Upcoming> {

            override fun onFailure(call: Call<Upcoming>, t: Throwable) {
                Log.e(TAG_DEBUC,"[Error getByGenre] "+t.message)
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
                    searchListLiveData.value = Pair( response.body()?.results ?: emptyList(), response.body()?.total_pages ?: 0)
                }else{
                    hasErrorLiveData.value = true
                    Log.e(TAG_DEBUC,"[Response getByGenre Error] code: "+response.code())
                }
            }
        })
    }
}