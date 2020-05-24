package com.manickchand.upcomingmovies.ui.home

import android.util.Log.i
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.repository.UpcomingMoviesRepository
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val upcomingMoviesRepository: UpcomingMoviesRepository) : BaseViewModel() {

    val moviesLiveData = MutableLiveData< Pair< List<Movie>?, Int> >()

    fun getUpcomingList(page:Int){

        launch{

            loading.value = true

            try {
                hasErrorLiveData.value = false
                moviesLiveData.value = upcomingMoviesRepository.getUpcomingList(page)
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
             withContext(IO) {
                 moviesLiveData.postValue(upcomingMoviesRepository.getAllFromDB())
            }
        }
    }
}