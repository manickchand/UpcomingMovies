package com.manickchand.upcomingmovies.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre  (
    var id:Int?,
    var name:String?
): Parcelable