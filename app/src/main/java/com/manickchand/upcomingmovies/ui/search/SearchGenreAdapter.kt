package com.manickchand.upcomingmovies.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.databinding.ItemGenreBinding
import com.manickchand.upcomingmovies.domain.models.Genre

class SearchGenreAdapter(
    private val list: List<Genre>,
    private val onItemClickListener: ((genre: Genre) -> Unit)
) : RecyclerView.Adapter<SearchGenreAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    inner class ViewHolder(
        private val binding: ItemGenreBinding,
        private val onItemClickListener: ((genre: Genre) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.genre = genre
            binding.root.setOnClickListener {
                onItemClickListener.invoke(genre)
            }
        }
    }
}