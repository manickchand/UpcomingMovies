package com.manickchand.upcomingmovies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.base.BaseFragment
import com.manickchand.upcomingmovies.domain.models.Genre
import com.manickchand.upcomingmovies.ui.searchList.SearchListActivity
import com.manickchand.upcomingmovies.utils.hasInternet
import com.manickchand.upcomingmovies.utils.showToast
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {

    private val searchViewModel by viewModel<SearchViewModel>()
    private var mList:MutableList<Genre> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchListener()

        with(rv_genres_search){
            layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = SearchGenreAdapter(context, mList){ genre ->
                //SEARCH BY GENRE
                val intent = SearchListActivity.getStartIntent(requireContext(), null, genre)
                requireActivity().startActivity(intent)
            }
        }

        searchViewModel.genreListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                mList.addAll(list)
                rv_genres_search.adapter!!.notifyDataSetChanged()
            }
        })

        searchViewModel.hasErrorLiveData.observe(viewLifecycleOwner, Observer {error ->
            if (error) requireContext().showToast(R.string.request_error)
        })

        searchViewModel.loading.observe(viewLifecycleOwner, Observer { load ->
            swiperefresh_genres.isRefreshing = load
        })

        swiperefresh_genres.setColorSchemeResources(R.color.colorBlack)
        swiperefresh_genres.setOnRefreshListener{
            fetchGenres()
        }

        fetchGenres()
    }

    private fun searchListener(){
        et_search.setOnEditorActionListener{ _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val str = et_search.text.toString()
                if(str.isNotEmpty()) {
                    val intent = SearchListActivity.getStartIntent(requireContext(), str, null)
                    requireActivity().startActivity(intent)
                }else{
                    requireContext().showToast(R.string.request_error)
                }
                true
            } else {
                false
            }
        }
    }

    private fun fetchGenres(){
        executeIfConnection {
            searchViewModel.getAllGenres()
        }
    }

}