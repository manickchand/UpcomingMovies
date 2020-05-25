package com.manickchand.upcomingmovies

import android.app.Application
import com.manickchand.upcomingmovies.di.appComponent
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class KoinTest : KoinTest{

    @Mock
    private lateinit var context: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun checkModules() {
        startKoin {
            androidContext(context)
            modules(appComponent)
        }.checkModules()
    }
}