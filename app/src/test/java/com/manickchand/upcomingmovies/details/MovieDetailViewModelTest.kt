package com.manickchand.upcomingmovies.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.data.repository.UpcomingMoviesRepository
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val movieMock = Movie(1,
        "path",
        "path",
        "Movie 1",
        "Movie 1",
        1.0,
        "Hello word",
        1,
        null,
        1.0,
        emptyList()
    )

    private val movieMockNull = Movie(2,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    private lateinit var upcomingMoviesRepository: UpcomingMoviesRepository
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        upcomingMoviesRepository = mock()
        whenever(
            runBlocking {upcomingMoviesRepository.getMovieDetail(1) }
        ).thenReturn(movieMock)

        whenever(
            runBlocking {upcomingMoviesRepository.getMovieDetail(2) }
        ).thenReturn(movieMockNull)
    }

    private fun instantiateViewModel(): MovieDetailViewModel {
        return MovieDetailViewModel(upcomingMoviesRepository)
    }

    @Test
    fun `when movie_id exist in api`(){
        val viewModel = instantiateViewModel()
        val movieLiveData = viewModel.movie
        val observer = mock() as Observer<Movie>

        movieLiveData.observeForever(observer)
        viewModel.getMovieDetail(1)
        verify(observer).onChanged(movieMock)
    }

    @Test
    fun `when movie_id not exist in api`(){
        val viewModel = instantiateViewModel()
        val movieLiveData = viewModel.movie
        val observer = mock() as Observer<Movie>

        movieLiveData.observeForever(observer)
        viewModel.getMovieDetail(0)
        verify(observer).onChanged(null)
    }

    @Test
    fun `when movie return with null fields`(){
        val viewModel = instantiateViewModel()
        val movieLiveData = viewModel.movie
        val observer = mock() as Observer<Movie>

        movieLiveData.observeForever(observer)
        viewModel.getMovieDetail(2)
        verify(observer).onChanged(movieMockNull)
    }
}