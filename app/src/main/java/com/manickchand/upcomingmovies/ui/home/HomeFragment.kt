package com.manickchand.upcomingmovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.base.BaseFragment
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailActivity
import com.manickchand.upcomingmovies.utils.showToast
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private var mList:MutableList<Movie> = ArrayList()
    private var pastVisiblesItems = 0
    private var totalItemCount = 0
    private var loading = false
    private var pageLoad = 1
    private var totalPages =0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()

        homeViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { pair ->
                mList.addAll(pair.first!!)
                totalPages = pair.second
                rv_upcoming_movies.adapter!!.notifyDataSetChanged()
                loading = false
            }
        })

        homeViewModel.hasErrorLiveData.observe(viewLifecycleOwner, Observer {error ->
            if (error) showToast("Error get upcomming list !")
        })

        swiperefresh.setColorSchemeResources(R.color.colorBlack)
        swiperefresh.setOnRefreshListener{
            fetchUpcoming()
        }

        homeViewModel.loading.observe(viewLifecycleOwner, Observer { load ->
            swiperefresh.isRefreshing = load
        })

        fetchUpcoming()
    }

    private fun setupRecyclerView(){

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

                        if (!loading && (pageLoad <= totalPages)) {
                            if (pastVisiblesItems >= totalItemCount-1) {

                                loading = true
                                pageLoad ++
                                fetchUpcoming()
                            }
                        }
                    }
                }
            })

            adapter = UpcomingAdapter(context, mList){ movie ->
                val intent = MovieDetailActivity.getStartIntent(requireContext(), movie.id)
                requireActivity().startActivity(intent)
            }
        }
    }

    fun fetchUpcoming(){
        executeIfConnection {
            homeViewModel.getUpcomingList(pageLoad)
        }
    }
}