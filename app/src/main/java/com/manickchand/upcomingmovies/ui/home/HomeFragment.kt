package com.manickchand.upcomingmovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.base.BaseFragment
import com.manickchand.upcomingmovies.databinding.FragmentHomeBinding
import com.manickchand.upcomingmovies.domain.models.Movie
import com.manickchand.upcomingmovies.domain.models.Upcoming
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailActivity
import com.manickchand.upcomingmovies.utils.ViewState
import com.manickchand.upcomingmovies.utils.addInfiniteScroll
import com.manickchand.upcomingmovies.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModel<HomeViewModel>()
    private var mList: MutableList<Movie> = ArrayList()
    private var pageLoad = 0
    private var totalPages = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()

        bindObserver()

        binding.swiperefresh.setOnRefreshListener {
            pageLoad = 0
            mList.clear()
            fetchUpcoming()
        }

        fetchUpcoming()
    }

    private fun bindObserver() {
        homeViewModel.getMoviesLiveData().observe(viewLifecycleOwner, { state ->
            when (state) {
                is ViewState.Success -> {
                    binding.swiperefresh.isRefreshing = false
                    addItems(state.data)
                }
                is ViewState.Loading -> {
                    binding.swiperefresh.isRefreshing = true
                }
                else -> {
                    binding.swiperefresh.isRefreshing = false
                    requireContext().showToast(R.string.request_error)
                }
            }
        })
    }

    private fun addItems(upcoming: Upcoming) {
        mList.addAll(upcoming.results)
        totalPages = upcoming.total_pages
        binding.rvUpcomingMovies.adapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {

        with(binding.rvUpcomingMovies) {

            layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addInfiniteScroll {
                if ((pageLoad <= totalPages)) {
                    fetchUpcoming()
                }
            }

            adapter = UpcomingAdapter(mList) { movie ->
                startActivity(
                    MovieDetailActivity.getStartIntent(requireContext(), movie.id)
                )
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