package com.manickchand.upcomingmovies.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre  (
    var id:Int?,
    var genres:String?
): Parcelable