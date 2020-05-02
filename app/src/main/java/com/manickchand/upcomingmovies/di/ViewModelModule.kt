package com.manickchand.upcomingmovies.di

import com.manickchand.upcomingmovies.ui.home.HomeViewModel
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailViewModel
import com.manickchand.upcomingmovies.ui.search.SearchViewModel
import com.manickchand.upcomingmovies.ui.searchList.SearchListViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { MovieDetailViewModel() }
    viewModel { SearchViewModel() }
    viewModel { SearchListViewModel() }
}