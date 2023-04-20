package com.iiitd.antonio20028.a5_trajroadsensingx.utils

import android.app.Activity
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun Activity.changeStatusBarColor(statusBarColor:Int?, navigationBarColor:Int?, isLight:Boolean){
    val window = window!!
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = statusBarColor?:window.statusBarColor
    window.navigationBarColor = navigationBarColor?: window.navigationBarColor
    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isLight
}