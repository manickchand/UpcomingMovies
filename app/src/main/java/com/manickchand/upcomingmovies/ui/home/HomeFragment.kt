package com.manickchand.upcomingmovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.base.BaseFragment
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.models.Upcoming
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailActivity
import com.manickchand.upcomingmovies.utils.ViewState
import com.manickchand.upcomingmovies.utils.addInfiniteScroll
import com.manickchand.upcomingmovies.utils.showToast
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private var mList: MutableList<Movie> = ArrayList()
    private var pageLoad = 0
    private var totalPages = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()

        bindObserver()

        swiperefresh.setOnRefreshListener {
            pageLoad = 0
            fetchUpcoming()
        }

        fetchUpcoming()
    }

    private fun bindObserver() {
        homeViewModel.getMoviesLiveData().observe(viewLifecycleOwner, { state ->
            when (state) {
                is ViewState.Success -> {
                    swiperefresh.isRefreshing = false
                    addItems(state.data)
                }
                is ViewState.Loading -> {
                    swiperefresh.isRefreshing = true
                }
                else -> {
                    swiperefresh.isRefreshing = false
                    requireContext().showToast(R.string.request_error)
                }
            }
        })
    }

    private fun addItems(upcoming: Upcoming) {
        mList.addAll(upcoming.results)
        totalPages = upcoming.total_pages
        rv_upcoming_movies.adapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {

        with(rv_upcoming_movies) {

            layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addInfiniteScroll {
                if ((pageLoad <= totalPages)) {
                    fetchUpcoming()
                }
            }

            adapter = UpcomingAdapter(mList) { movie ->
                val intent = MovieDetailActivity.getStartIntent(requireContext(), movie.id)
                requireActivity().startActivity(intent)
            }
        }
    }

    private fun fetchUpcoming() {
        executeIfConnection {
            if(it){
                pageLoad++
                homeViewModel.getUpcomingList(pageLoad)
            }else{
                homeViewModel.getByDb()
            }
        }
    }
}