package com.manickchand.upcomingmovies.ui.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.databinding.ActivityMovieDetailBinding
import com.manickchand.upcomingmovies.domain.models.Genre
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity() {

    private val movieDetailViewModel by viewModel<MovieDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        val binding = DataBindingUtil
            .setContentView<ActivityMovieDetailBinding>( this, R.layout.activity_movie_detail )

        binding.lifecycleOwner = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        binding.viewModel = movieDetailViewModel

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID,0)

        movieDetailViewModel.getMovieDetail(movieId)

        movieDetailViewModel.hasErrorLiveData.observe(this, Observer {
            if(it){
                movieDetailViewModel.getMovie(movieId)
            }else{
                movieDetailViewModel.insertMovie(movieDetailViewModel.movie.value!!)
            }
        })

    }

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun getStartIntent(context: Context, movieId:Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:genres")
        fun setGenres(textView: AppCompatTextView, genres: List<Genre>? ) {
            if (genres != null) {
                textView.text = genres.joinToString { "${it.name}" }
            }
        }

        @JvmStatic
        @BindingAdapter("bind:navigationClick")
        fun navigationClick(bar: Toolbar, context: Context ) {
            bar.setNavigationOnClickListener {
                (context as AppCompatActivity).finish()
            }
        }
    }
}
