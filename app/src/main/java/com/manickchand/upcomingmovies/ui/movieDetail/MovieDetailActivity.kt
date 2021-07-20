package com.manickchand.upcomingmovies.ui.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.manickchand.upcomingmovies.databinding.ActivityMovieDetailBinding
import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.utils.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<MovieDetailViewModel>()
    private lateinit var binding: ActivityMovieDetailBinding
    private val movieId by lazy { intent.getIntExtra(EXTRA_MOVIE_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        bindObserver()
        viewModel.getMovieDetail(movieId)
    }

    private fun bindObserver() {
        viewModel.getMovieDetail().observe(this, { state ->
            when (state) {
                is ViewState.Success -> {
                    binding.pbDetailMovie.isVisible = false
                    binding.movie = state.data
                    viewModel.insertMovie(state.data)
                }
                is ViewState.Loading -> {
                    binding.pbDetailMovie.isVisible = true
                }
                else -> {
                    binding.pbDetailMovie.isVisible = false
                    viewModel.getMovie(movieId)
                }
            }
        })
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun getStartIntent(context: Context, movieId: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:genres")
        fun setGenres(textView: AppCompatTextView, genres: List<Genre>?) {
            textView.text = genres?.joinToString { "${it.name}" } ?: ""
        }

        @JvmStatic
        @BindingAdapter("bind:navigationClick")
        fun navigationClick(bar: Toolbar, context: Context) {
            bar.setNavigationOnClickListener {
                (context as AppCompatActivity).finish()
            }
        }
    }
}
