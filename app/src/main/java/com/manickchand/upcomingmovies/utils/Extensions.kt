package com.manickchand.upcomingmovies.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Context.showToast(msgRes: Int) = Toast.makeText(this, getString(msgRes), Toast.LENGTH_SHORT).show()

fun <T> MutableLiveData<T>.toImmutable(): LiveData<T> = this

fun RecyclerView.addInfiniteScroll(callMore: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(
            recyclerView: RecyclerView, dx: Int, dy: Int
        ) {
            if (dy > 0) {
                layoutManager?.let { manager ->
                    if ((manager as LinearLayoutManager).findLastVisibleItemPosition() >= manager.itemCount - 1) {
                        callMore()
                    }
                }
            }
        }
    })
}