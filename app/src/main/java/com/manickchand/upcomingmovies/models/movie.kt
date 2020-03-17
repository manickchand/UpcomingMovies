package com.manickchand.upcomingmovies.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Movie  (
    var id:Int?,
    var poster_path:String?,
    var backdrop_path:String?,
    var title:String?,
    var original_title:String?,
    var popularity:Double?,
    var overview:String?,
    var vote_count:Int?,
    var release_date: Date?,
    var vote_average:Double?,
    var genres:List<Genre>?
): Parcelable