package com.manickchand.upcomingmovies.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.databinding.ItemMovieBinding
import com.manickchand.upcomingmovies.domain.models.Movie

class UpcomingAdapter(
    private val list: List<Movie>,
    private val onItemClickListener: ((movie: Movie) -> Unit)
) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =  holder.bindMovie(list[position])
    override fun getItemCount() = list.size

    inner class ViewHolder(
        private val binding: ItemMovieBinding,
        private val onItemClickListener: ((movie: Movie) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindMovie(movie: Movie) {
            binding.movie = movie
            binding.root.setOnClickListener {
                onItemClickListener.invoke(movie)
            }
        }
    }
}