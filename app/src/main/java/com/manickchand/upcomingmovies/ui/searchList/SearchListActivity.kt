package com.manickchand.upcomingmovies.ui.searchList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.databinding.ActivitySearchListBinding
import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailActivity
import com.manickchand.upcomingmovies.utils.ViewState
import com.manickchand.upcomingmovies.utils.addInfiniteScroll
import com.manickchand.upcomingmovies.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchListActivity : AppCompatActivity() {

    private val viewModel by viewModel<SearchListViewModel>()
    private lateinit var binding: ActivitySearchListBinding
    private val mList: MutableList<Movie> = ArrayList()

    private var pageLoad = 0
    private var totalPages = 0

    private val query by lazy { intent.getStringExtra(EXTRA_QUERY) }
    private val genre by lazy { intent.getSerializableExtra(EXTRA_GENRE) as Genre? }

    companion object {
        private const val EXTRA_QUERY = "EXTRA_QUERY"
        private const val EXTRA_GENRE = "EXTRA_GENRE"

        fun getStartIntent(context: Context, query: String?, genre: Genre?) =
            Intent(context, SearchListActivity::class.java).apply {
                putExtra(EXTRA_QUERY, query)
                putExtra(EXTRA_GENRE, genre)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindObserver()
        setupToolbar()
        setupRecyclerView()
        fetchData()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.apply {
            setNavigationOnClickListener { finish() }
            title = if (!query.isNullOrEmpty()) query
            else genre?.name ?: ""
        }
    }

    private fun bindObserver() {
        viewModel.getSearchListLiveData().observe(this, { state ->
            when (state) {
                is ViewState.Success -> {
                    mList.addAll(state.data.results)
                    totalPages = state.data.total_pages
                    binding.rvSearchMovies.adapter?.notifyDataSetChanged()
                    binding.pbSearchMovie.isVisible = false
                }
                is ViewState.Loading -> {
                    binding.pbSearchMovie.isVisible = true
                }
                else -> {
                    binding.pbSearchMovie.isVisible = false
                    showToast(R.string.request_error)
                }
            }
        })
    }

    private fun setupRecyclerView() {

        with(binding.rvSearchMovies) {

            setHasFixedSize(true)
            addInfiniteScroll {
                if ((pageLoad <= totalPages)) {
                    fetchData()
                }
            }

            adapter = SearchListAdapter(mList) { movie ->
                val intent = MovieDetailActivity.getStartIntent(this@SearchListActivity, movie.id)
                startActivity(intent)
            }
        }
    }

    private fun fetchData() {
        pageLoad++
        if (!query.isNullOrEmpty())
            viewModel.searchMovies(query!!, pageLoad)
        else genre?.id?.let {
            viewModel.getByGenre(it, pageLoad)
        }
    }

}
