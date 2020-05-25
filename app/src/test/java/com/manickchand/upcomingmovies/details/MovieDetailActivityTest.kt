package com.manickchand.upcomingmovies.details

import android.os.Build
import com.manickchand.upcomingmovies.ui.movieDetail.MovieDetailActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class MovieDetailActivityTest{

    private lateinit var activity: MovieDetailActivity

    @Before
    fun setUp() {
        activity =  Robolectric.buildActivity(MovieDetailActivity::class.java)
            .create()
            .visible()
            .get()
    }

    @Test
    @Throws(Exception::class)
    fun check_Activity_Not_Null() {
        Assert.assertNotNull(activity)
    }

}