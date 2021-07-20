package com.manickchand.upcomingmovies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.base.BaseFragment
import com.manickchand.upcomingmovies.databinding.FragmentSearchBinding
import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.ui.searchList.SearchListActivity
import com.manickchand.upcomingmovies.utils.ViewState
import com.manickchand.upcomingmovies.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private var mList: MutableList<Genre> = ArrayList()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding
        .inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchListener()
        bindObserver()
        setupRecyclerView()
        binding.swiperefreshGenres.setOnRefreshListener {
            fetchGenres()
        }
        fetchGenres()
    }

    private fun bindObserver() {
        viewModel.getGenreListLiveData().observe(viewLifecycleOwner, { state ->
            when (state) {
                is ViewState.Success -> {
                    mList.clear()
                    mList.addAll(state.data)
                    binding.rvGenresSearch.adapter?.notifyDataSetChanged()
                    binding.swiperefreshGenres.isRefreshing = false
                }
                is ViewState.Loading -> {
                    binding.swiperefreshGenres.isRefreshing = true
                }
                else -> {
                    binding.swiperefreshGenres.isRefreshing = false
                    requireContext().showToast(R.string.request_error)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        with(binding.rvGenresSearch) {
            layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = SearchGenreAdapter(mList) { genre ->
                goToSearchListActivity(genre = genre)
            }
        }
    }

    private fun searchListener() {
        binding.etSearch.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = text.toString()
                    if (query.isNotEmpty()) {
                        goToSearchListActivity(query = query)
                    } else {
                        requireContext().showToast(R.string.request_error)
                    }
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun goToSearchListActivity(query: String? = null, genre: Genre? = null) {
        val intent = SearchListActivity.getStartIntent(requireContext(), query, genre)
        requireActivity().startActivity(intent)
    }

    private fun fetchGenres() {
        executeIfConnection {
            viewModel.getAllGenres()
        }
    }

}