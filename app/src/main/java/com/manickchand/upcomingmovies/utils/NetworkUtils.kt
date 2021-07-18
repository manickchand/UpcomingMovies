package com.manickchand.upcomingmovies.utils

import android.content.Context
import android.net.ConnectivityManager

fun hasInternet(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.isConnectedOrConnecting == true
}

interface IConnectionUtils {
    fun executeIfConnection(hasConnection: () -> Unit)
}
