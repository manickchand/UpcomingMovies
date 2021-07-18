package com.manickchand.upcomingmovies.ui.searchList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailActivity
import com.manickchand.upcomingmovies.utils.IConnectionUtils
import com.manickchand.upcomingmovies.utils.hasInternet
import com.manickchand.upcomingmovies.utils.showToast
import kotlinx.android.synthetic.main.activity_search_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchListActivity : AppCompatActivity(), IConnectionUtils {

    private val searchListViewModel by viewModel<SearchListViewModel>() 
    private var mList:MutableList<Movie> = ArrayList()
    private var pastVisiblesItems = 0
    private var totalItemCount = 0
    private var loading = false
    private var pageLoad = 1
    private var totalPages =0

    companion object {
        private const val EXTRA_QUERY = "EXTRA_QUERY"
        private const val EXTRA_GENRE = "EXTRA_GENRE"
        private var query:String? = null
        private var genre:Genre? = null

        fun getStartIntent(context: Context, query: String?, genre:Genre?): Intent {
            return Intent(context, SearchListActivity::class.java).apply {
                putExtra(EXTRA_QUERY, query)
                putExtra(EXTRA_GENRE, genre)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        query = intent.getStringExtra(EXTRA_QUERY)
        genre = intent.getSerializableExtra(EXTRA_GENRE) as Genre?

        if(!query.isNullOrEmpty()){
            title = query
        }else if(genre!=null){
            title = genre?.name
        }

        setupRecyclerView()


        searchListViewModel.searchListLiveData.observe(this, Observer {
            it?.let { pair ->
                mList.addAll(pair.first!!)
                totalPages = pair.second
                rv_search_movies.adapter!!.notifyDataSetChanged()
                loading = false
            }
        })

        searchListViewModel.hasErrorLiveData.observe(this, Observer {error ->
            if (error) showToast("Error search movies !")
        })

        searchListViewModel.loading.observe(this, Observer { load ->
            if(load) pb_search_movie.visibility = View.VISIBLE
            else pb_search_movie.visibility = View.GONE
        })

        checkConnection()
    }

    private fun setupRecyclerView(){

        with(rv_search_movies){

            layoutManager = LinearLayoutManager(this@SearchListActivity,  RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView, dx: Int, dy: Int
                ) {
                    if (dy > 0)
                    {

                        totalItemCount = layoutManager!!.itemCount
                        pastVisiblesItems = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                        if (!loading && (pageLoad <= totalPages)) {
                            if (pastVisiblesItems >= totalItemCount-1) {

                                loading = true
                                pageLoad ++
                                checkConnection()
                            }
                        }
                    }
                }
            })

            adapter = SearchListAdapter(context, mList){ movie ->
                val intent = MovieDetailActivity.getStartIntent(this@SearchListActivity, movie.id)
                startActivity(intent)
            }
        }
    }

    override fun checkConnection() {
        if(hasInternet(this)){
            if(!query.isNullOrEmpty()){
                searchListViewModel.searchMovies(query!!, pageLoad)
            }else if(genre!=null){
                searchListViewModel.getByGenre(genre!!.id!!, pageLoad)
            }
        }else{
            showToast("Connection error !")
        }
    }

}
