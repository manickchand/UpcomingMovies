package com.manickchand.upcomingmovies.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.showToast(msg:String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Fragment.showToast(msg:String) = Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()