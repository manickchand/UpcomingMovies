package com.manickchand.upcomingmovies.ui.searchList

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailActivity
import com.manickchand.upcomingmovies.utils.IConnectionUtils
import com.manickchand.upcomingmovies.utils.hasInternet
import kotlinx.android.synthetic.main.activity_search_list.*

class SearchListActivity : AppCompatActivity(), IConnectionUtils {

    private lateinit var searchListViewModel: SearchListViewModel
    private var mList:MutableList<Movie> = ArrayList()
    private var pastVisiblesItems = 0
    private var totalItemCount = 0
    private var loading = false
    private var pageLoad = 1
    private var totalPages =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)

        query = intent.getStringExtra(EXTRA_QUERY)

        searchListViewModel =
            ViewModelProviders.of(this).get(SearchListViewModel::class.java)

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
            if (error) Toast.makeText(this, "Error search movies !", Toast.LENGTH_SHORT).show()
        })

        searchListViewModel.loading.observe(this, Observer { load ->
            if(load) pb_search_movie.visibility = View.VISIBLE
            else pb_search_movie.visibility = View.GONE
        })

        checkConnection()
    }

    fun setupRecyclerView(){

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
                val intent = MovieDetailActivity.getStartIntent(this@SearchListActivity, movie.id!!)
                startActivity(intent)
            }
        }
    }

    override fun checkConnection() {
        if(hasInternet(this)){
            searchListViewModel.searchMovies(query, pageLoad)
        }else{
            Toast.makeText(this, "Connection error !", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val EXTRA_QUERY = "EXTRA_QUERY"
        private var query = ""

        fun getStartIntent(context: Context, query: String): Intent {
            return Intent(context, SearchListActivity::class.java).apply {
                putExtra(EXTRA_QUERY, query)
            }
        }
    }


}
