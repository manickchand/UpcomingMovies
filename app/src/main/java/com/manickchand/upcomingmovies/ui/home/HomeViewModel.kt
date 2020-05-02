package com.manickchand.upcomingmovies.ui.home

import android.util.Log.i
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.repository.IServiceRetrofit
import com.manickchand.upcomingmovies.repository.MovieDAO
import com.manickchand.upcomingmovies.utils.EN_US
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.TOKEN_API
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val service: IServiceRetrofit, private val database: MovieDAO) : BaseViewModel() {

    val moviesLiveData = MutableLiveData< Pair< List<Movie>?, Int> >()

    fun getUpcomingList(page:Int){

        launch{

            loading.value = true

            try {
                val results = service.getUpcomingList(TOKEN_API, EN_US , page)
                hasErrorLiveData.value = false
                moviesLiveData.value = Pair(results.results ?: emptyList() , results.total_pages ?: 0)
            }catch (t:Throwable){
                hasErrorLiveData.value = true
                i(TAG_DEBUC, "[error] getUpcomingList: ${t.message}")
            }finally {
                loading.value = false
            }
        }

    }

     fun getByDb(){
        launch {
            var list:List<Movie> = emptyList()
            withContext(IO) {
                try {
                    list =database.getAll()
                }catch (t:Throwable){
                    i(TAG_DEBUC, "[error] getByDb: ${t.message}")
                }
            }
            moviesLiveData.value = Pair(list , 1)
        }
    }
}