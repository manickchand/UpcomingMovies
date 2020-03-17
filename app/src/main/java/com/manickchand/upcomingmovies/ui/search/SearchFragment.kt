package com.manickchand.upcomingmovies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.base.BaseFragment
import com.manickchand.upcomingmovies.models.Genre
import com.manickchand.upcomingmovies.utils.hasInternet
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var mList:MutableList<Genre> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(rv_genres_search){
            layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = SearchGenreAdapter(context, mList){ genre ->
                Toast.makeText(activity, "Click ${genre.name}", Toast.LENGTH_SHORT).show()
            }
        }

        searchViewModel.genreListLiveData.observe(this, Observer {
            it?.let { list ->
                mList.addAll(list)
                rv_genres_search.adapter!!.notifyDataSetChanged()
            }
        })

        searchViewModel.hasErrorLiveData.observe(this, Observer {error ->
            if (error) Toast.makeText(context, "Error get genre list !", Toast.LENGTH_SHORT).show()
        })

        swiperefresh_genres.setColorSchemeResources(R.color.colorBlack)
        swiperefresh_genres.setOnRefreshListener{
            this.checkConnection()
        }

        searchViewModel.loading.observe(this, Observer { load ->
            swiperefresh_genres.isRefreshing = load
        })

        checkConnection()
    }

    override fun checkConnection() {
        if(hasInternet(activity)){
            searchViewModel.getAllGenres()
        }else{
            Toast.makeText(context, "Connection error !", Toast.LENGTH_SHORT).show()
        }
    }

}