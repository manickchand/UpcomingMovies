package com.manickchand.upcomingmovies.base

import androidx.fragment.app.Fragment
import com.manickchand.upcomingmovies.R
import com.manickchand.upcomingmovies.utils.IConnectionUtils
import com.manickchand.upcomingmovies.utils.hasInternet
import com.manickchand.upcomingmovies.utils.showToast

open class BaseFragment : Fragment(), IConnectionUtils {
    override fun executeIfConnection(hasConnection: () -> Unit) {
        if (hasInternet(requireContext())) hasConnection()
        else requireContext().showToast(R.string.connection_error)
    }
}