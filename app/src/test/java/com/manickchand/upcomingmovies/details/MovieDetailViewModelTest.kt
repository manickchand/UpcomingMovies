package com.manickchand.upcomingmovies.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.models.Upcoming
import com.manickchand.upcomingmovies.domain.useCase.MoviesUseCase
import com.manickchand.upcomingmovies.testUtils.StubMovieFactory
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailViewModel
import com.manickchand.upcomingmovies.utils.ViewState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val moviesUseCase: MoviesUseCase = mock()
    private lateinit var viewModelTest: MovieDetailViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModelTest = MovieDetailViewModel(moviesUseCase)
    }

    @Test
    fun `success return data`() {

        whenever(
            runBlocking { moviesUseCase.getMovieDetail(anyInt()) }
        ).thenReturn(StubMovieFactory.stubMovie())

        viewModelTest.getMovieDetail(0)
        val result = viewModelTest.getMovieDetail()

        assertTrue(result.value is ViewState.Success)
    }

    @Test
    fun `when return null`() {

        whenever(
            runBlocking { moviesUseCase.getMovieDetail(anyInt()) }
        ).thenReturn(null)

        viewModelTest.getMovieDetail(0)
        val result = viewModelTest.getMovieDetail()
        assertTrue(result.value is ViewState.Success)
        assertTrue(result.value is ViewState.Success)
    }
//
//    @Test
//    fun `when movie return with null fields`() {
//        val viewModel = instantiateViewModel()
//        val movieLiveData = viewModel.movie
//        val observer = mock() as Observer<Movie>
//
//        movieLiveData.observeForever(observer)
//        viewModel.getMovieDetail(2)
//        verify(observer).onChanged(movieMockNull)
//    }
}