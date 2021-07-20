package com.manickchand.upcomingmovies.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.domain.useCase.GenresUseCase
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import com.manickchand.upcomingmovies.utils.ViewState
import com.manickchand.upcomingmovies.utils.toImmutable
import kotlinx.coroutines.launch

class SearchViewModel(private val genresUseCase: GenresUseCase) : BaseViewModel() {

    private val genreListLiveData = MutableLiveData<ViewState<List<Genre>>>()
    fun getGenreListLiveData() = genreListLiveData.toImmutable()

    fun getAllGenres(){

        launch {
            genreListLiveData.value = ViewState.Loading

            try {
                genreListLiveData.value = ViewState.Success(genresUseCase.getAllGenres())
            }catch (t:Throwable){
                genreListLiveData.value = ViewState.Failed(t)
                Log.e(TAG_DEBUC,"[Error getAllGenres] "+t.message)
            }
        }
    }
}