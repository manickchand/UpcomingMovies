package com.manickchand.upcomingmovies.ui.searchList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.upcomingmovies.databinding.ItemSearchBinding
import com.manickchand.upcomingmovies.domain.models.Movie

class SearchListAdapter(context: Context,
                        list: List<Movie>,
                        private val onItemClickListener:((movie:Movie) -> Unit) ) : RecyclerView.Adapter<SearchListAdapter.MyViewHolder?>() {

    private var mContext =context
    private var mList = list
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val binding = ItemSearchBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding,onItemClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindMovie(mList[position])
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount() = mList.count()

    inner class MyViewHolder(private val binding: ItemSearchBinding,
                             private val onItemClickListener: ((movie: Movie) -> Unit)) :RecyclerView.ViewHolder(binding.root){

        fun bindMovie(movie: Movie) {
            binding.movie = movie
            binding.root.setOnClickListener{
                onItemClickListener.invoke(movie)
            }
        }
    }
}