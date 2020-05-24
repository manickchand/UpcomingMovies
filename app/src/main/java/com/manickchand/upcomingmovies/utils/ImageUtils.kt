package com.manickchand.upcomingmovies.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.manickchand.upcomingmovies.R
import com.squareup.picasso.Picasso
import com.manickchand.upcomingmovies.BuildConfig

class ImageUtils {

    companion object{

        @JvmStatic
        @BindingAdapter("bind:picassoLoad")
        fun loadImageView2(image: ImageView, imageUrl: String?) {
            if(imageUrl.isNullOrEmpty().not()){

                val url = BuildConfig.IMAGE_URL + imageUrl

                Picasso.get().load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(image)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:src")
        fun setImageResource( iv: ImageView,  image: Int ) {
            iv.setImageResource( image )
        }
    }
}

