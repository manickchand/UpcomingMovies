package com.manickchand.upcomingmovies.ui.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.databinding.ActivityMovieDetailBinding
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val binding = DataBindingUtil
            .setContentView<ActivityMovieDetailBinding>( this, R.layout.activity_movie_detail )

        binding.lifecycleOwner = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)

        binding.viewModel = movieDetailViewModel

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID,0)

        movieDetailViewModel.getMovieDetail(movieId)

    }

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun getStartIntent(context: Context, movieId:Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
        }
    }
}
