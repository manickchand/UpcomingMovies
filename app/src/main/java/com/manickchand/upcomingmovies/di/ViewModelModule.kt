package com.manickchand.upcomingmovies.di

import com.manickchand.upcomingmovies.ui.home.HomeViewModel
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailViewModel
import com.manickchand.upcomingmovies.ui.search.SearchViewModel
import com.manickchand.upcomingmovies.ui.searchList.SearchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MovieDetailViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SearchListViewModel(get()) }
}