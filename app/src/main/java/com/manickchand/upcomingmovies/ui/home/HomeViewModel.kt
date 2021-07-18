package com.manickchand.upcomingmovies.ui.home

import android.util.Log.i
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.useCase.MoviesUseCase
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val moviesUseCase: MoviesUseCase) : BaseViewModel() {

    val moviesLiveData = MutableLiveData< Pair< List<Movie>?, Int> >()

    fun getUpcomingList(page:Int){

        launch{

            loading.value = true

            try {
                hasErrorLiveData.value = false
                moviesLiveData.value = moviesUseCase.getUpcomingList(page)
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
                 moviesLiveData.postValue(moviesUseCase.getAllFromDB())
            }
        }
    }
}