package com.manickchand.upcomingmovies.utils

import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.manickchand.upcomingmovies.R

class GenreUtils {

    companion object{
        private fun getGenreColor(id: Int): Int {
            return when (id) {
                28, 10749 -> R.color.red
                12, 16 -> R.color.lightRed
                99, 36, 37 -> R.color.brown
                35, 10770 -> R.color.teal
                80,18, 27 -> R.color.grey
                14, 9648, 878 -> R.color.purple
                10751 -> R.color.yellow
                else -> R.color.blue
            }
        }

        @JvmStatic
        @BindingAdapter("bind:cardBackgroundColor")
        fun setBackgroundCardColor(card: CardView, genreId: Int ) {
            card.setBackgroundResource(getGenreColor(genreId))
        }
    }
}