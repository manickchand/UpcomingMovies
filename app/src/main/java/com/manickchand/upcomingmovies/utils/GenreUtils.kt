package com.manickchand.upcomingmovies.utils

import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.domain.models.Genre

class GenreUtils {

    companion object{
        @JvmStatic
        fun getGenreColor(genre: Genre): Int {
            return when (genre.id) {
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
        fun setBackgroundCardColor(card: CardView, color: Int ) {
            card.setBackgroundResource(color)
        }
    }
}