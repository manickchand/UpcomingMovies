package com.manickchand.upcomingmovies.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val hasErrorLiveData = MutableLiveData<Boolean>()
}