package com.manickchand.upcomingmovies.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context.showToast(msgRes: Int) = Toast.makeText(this, getString(msgRes), Toast.LENGTH_SHORT).show()

fun Fragment.showToast(msg:String) = Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()