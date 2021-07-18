package com.manickchand.upcomingmovies.domain.models

import java.io.Serializable

data class GenreList  (
    var genres:List<Genre>
)

data class Genre  (
    var id:Int?,
    var name:String?
): Serializable