package com.manickchand.upcomingmovies.ui.searchList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.databinding.ItemSearchBinding
import com.manickchand.upcomingmovies.domain.models.Movie

class SearchListAdapter(
    private val list: List<Movie>,
    private val onItemClickListener: ((movieId: Int) -> Unit)
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])
    override fun getItemCount() = list.size

    inner class ViewHolder(
        private val binding: ItemSearchBinding,
        private val onItemClickListener: ((movieId: Int) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.root.setOnClickListener {
                onItemClickListener.invoke(movie.id)
            }
        }
    }
}