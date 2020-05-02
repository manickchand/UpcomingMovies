package com.manickchand.upcomingmovies.models

data class Upcoming  (
    var total_results:Int?,
    var total_pages:Int?,
    var page:Int?,
    var results:List<Movie>?
)