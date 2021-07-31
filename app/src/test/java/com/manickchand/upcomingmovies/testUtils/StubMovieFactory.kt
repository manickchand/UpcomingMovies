package com.manickchand.upcomingmovies.testUtils

import com.manickchand.upcomingmovies.domain.models.Movie

object StubMovieFactory {

    fun stubMovie() = Movie(
        0,
        "path",
        "path",
        "Movie 1",
        "Movie 1",
        1.0,
        "Hello word",
        1,
        null,
        1.0,
        emptyList()
    )

    fun stubList() = 1.rangeTo(5).map { stubMovie() }

    fun stubMovieNull() = Movie(
        2,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

}