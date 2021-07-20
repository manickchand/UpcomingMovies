package com.manickchand.upcomingmovies.ui.searchList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.domain.models.Upcoming
import com.manickchand.upcomingmovies.domain.useCase.GenresUseCase
import com.manickchand.upcomingmovies.domain.useCase.MoviesUseCase
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.ViewState
import com.manickchand.upcomingmovies.utils.toImmutable
import kotlinx.coroutines.launch

class SearchListViewModel(
    private val moviesUseCase: MoviesUseCase,
    private val genresUseCase: GenresUseCase
) : BaseViewModel() {

    private val searchListLiveData = MutableLiveData<ViewState<Upcoming>>()
    fun getSearchListLiveData() = searchListLiveData.toImmutable()

    fun searchMovies(query: String, page: Int) {
        launch {
            searchListLiveData.value = ViewState.Loading
            try {
                searchListLiveData.value =
                    ViewState.Success(moviesUseCase.searchMovies(query, page))
            } catch (t: Throwable) {
                searchListLiveData.value = ViewState.Failed(t)
                Log.e(TAG_DEBUC, "[Error searchMovies] " + t.message)
            }
        }
    }

    fun getByGenre(id: Int, page: Int) {
        launch {
            searchListLiveData.value = ViewState.Loading
            try {
                searchListLiveData.value = ViewState.Success(genresUseCase.getByGenre(id, page))
            } catch (t: Throwable) {
                searchListLiveData.value = ViewState.Failed(t)
                Log.e(TAG_DEBUC, "[Error searchMovies] " + t.message)
            }
        }
    }
}