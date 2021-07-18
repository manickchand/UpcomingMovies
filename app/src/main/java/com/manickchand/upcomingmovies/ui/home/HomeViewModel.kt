package com.manickchand.upcomingmovies.ui.home

import android.util.Log.i
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.domain.models.Upcoming
import com.manickchand.upcomingmovies.domain.useCase.MoviesUseCase
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.ViewState
import com.manickchand.upcomingmovies.utils.toImmutable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val moviesUseCase: MoviesUseCase) : BaseViewModel() {

    private val moviesLiveData = MutableLiveData<ViewState<Upcoming>>()
    fun getMoviesLiveData() = moviesLiveData.toImmutable()

    fun getUpcomingList(page: Int) {

        moviesLiveData.value = ViewState.Loading
        launch {
            try {
                val result = moviesUseCase.getUpcomingList(page)
                moviesLiveData.value = ViewState.Success(result)
            } catch (t: Throwable) {
                moviesLiveData.value = ViewState.Failed(t)
                i(TAG_DEBUC, "[error] getUpcomingList: ${t.message}")
            }
        }
    }

    fun getByDb() {
        moviesLiveData.value = ViewState.Loading
        launch {
            withContext(IO) {
                try {
                    moviesLiveData.postValue(ViewState.Success(moviesUseCase.getAllFromDB()))
                } catch (t: Throwable) {
                    moviesLiveData.value = ViewState.Failed(t)
                    i(TAG_DEBUC, "[error] getFromDB: ${t.message}")
                }
            }
        }
    }
}