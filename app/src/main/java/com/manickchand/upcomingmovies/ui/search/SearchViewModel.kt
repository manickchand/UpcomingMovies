package com.manickchand.upcomingmovies.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manickchand.upcomingmovies.base.BaseViewModel
import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.domain.useCase.GenresUseCase
import com.manickchand.upcomingmovies.utils.TAG_DEBUC
import kotlinx.coroutines.launch

class SearchViewModel(private val genresUseCase: GenresUseCase) : BaseViewModel() {

    val genreListLiveData = MutableLiveData< List<Genre> >()

    fun getAllGenres(){

        launch {
            loading.value = true

            try {
                hasErrorLiveData.value = false
                genreListLiveData.value = genresUseCase.getAllGenres()
            }catch (t:Throwable){
                hasErrorLiveData.value = true
                Log.e(TAG_DEBUC,"[Error getAllGenres] "+t.message)
            }finally {
                loading.value = false
            }
        }
    }
}