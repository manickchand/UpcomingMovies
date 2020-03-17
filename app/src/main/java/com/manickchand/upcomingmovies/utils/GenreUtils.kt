package com.manickchand.upcomingmovies.utils

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.manickchand.upcomingmovies.models.Genre

class GenreUtils {

    companion object{

        @JvmStatic
        @BindingAdapter("bind:genres")
        fun setImageResource(textView: AppCompatTextView, genres: List<Genre>? ) {
            if (genres != null) {
                textView.text = genres.joinToString { "${it.name}" }
            }
        }
    }
}