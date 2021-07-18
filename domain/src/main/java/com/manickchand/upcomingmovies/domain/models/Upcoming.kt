package com.manickchand.upcomingmovies.domain.models

data class Upcoming(
    val total_pages: Int,
    val results: List<Movie>
)