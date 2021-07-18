package com.manickchand.upcomingmovies.domain.models

data class Upcoming(
    var total_pages: Int?,
    var page: Int?,
    var results: List<Movie>?
)