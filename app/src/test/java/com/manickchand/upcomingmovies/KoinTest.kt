package com.manickchand.upcomingmovies

import android.app.Application
import com.manickchand.upcomingmovies.di.appComponent
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class KoinTest : KoinTest{

    private val context: Application = mock()

    @Test
    fun `check koin modules`() {
        startKoin {
            androidContext(context)
            modules(appComponent)
        }.checkModules()
    }
}