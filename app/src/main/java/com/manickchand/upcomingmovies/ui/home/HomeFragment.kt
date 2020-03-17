package com.manickchand.upcomingmovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.base.BaseFragment
import com.manickchand.upcomingmovies.models.Movie
import com.manickchand.upcomingmovies.utils.hasInternet
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mList:MutableList<Movie> = ArrayList()
    private var pastVisiblesItems = 0
    private var totalItemCount = 0
    private var loading = false
    private var pageLoad = 1
    private var total_pages =0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()

        homeViewModel.moviesLiveData.observe(this, Observer {
            it?.let { pair ->
                mList.addAll(pair.first!!)
                total_pages = pair.second
                rv_upcoming_movies.adapter!!.notifyDataSetChanged()
                loading = false
            }
        })

        homeViewModel.hasErrorLiveData.observe(this, Observer {error ->
            if (error) Toast.makeText(context, "Error get upcomming list !", Toast.LENGTH_SHORT).show()
        })

        swiperefresh.setColorSchemeResources(R.color.colorBlack)
        swiperefresh.setOnRefreshListener{
            this.checkConnection()
        }

        homeViewModel.loading.observe(this, Observer { load ->
            swiperefresh.isRefreshing = load
        })

        checkConnection()
    }

    fun setupRecyclerView(){

        with(rv_upcoming_movies){

            layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView, dx: Int, dy: Int
                ) {
                    if (dy > 0)
                    {

                        totalItemCount = layoutManager!!.itemCount
                        pastVisiblesItems = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                        if (!loading && (pageLoad <= total_pages)) {
                            if (pastVisiblesItems >= totalItemCount-1) {

                                loading = true
                                pageLoad ++
                                checkConnection()
                            }
                        }
                    }
                }
            })

            adapter = UpcomingAdapter(context, mList){ movie ->
//                val intent = AnimeDetailActivity.getStartIntent(activity!!, anime.mal_id!!)
//                activity!!.startActivity(intent)
            }

        }
    }

    override fun checkConnection(){
        if(hasInternet(activity)){
            homeViewModel.getUpcomingList(pageLoad)
        }else{
            Toast.makeText(context, "Connection error !", Toast.LENGTH_SHORT).show()
        }
    }
}