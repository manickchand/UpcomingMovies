package com.manickchand.upcomingmovies.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "movies")
data class Movie  (
    @PrimaryKey var id:Int,
    @ColumnInfo(name = "poster_path") var poster_path:String?,
    @ColumnInfo(name = "backdrop_path") var backdrop_path:String?,
    @ColumnInfo(name = "title") var title:String?,
    @ColumnInfo(name = "original_title") var original_title:String?,
    @ColumnInfo(name = "popularity") var popularity:Double?,
    @ColumnInfo(name = "overview") var overview:String?,
    @ColumnInfo(name = "vote_count") var vote_count:Int?,
    @Ignore var release_date: Date?,
    @ColumnInfo(name = "vote_average") var vote_average:Double?,
    @Ignore var genres:List<Genre>?
){
    constructor() : this(
        0,null,
        null, null,
        null,null,
        null,null,
        null,null,
        null)
}