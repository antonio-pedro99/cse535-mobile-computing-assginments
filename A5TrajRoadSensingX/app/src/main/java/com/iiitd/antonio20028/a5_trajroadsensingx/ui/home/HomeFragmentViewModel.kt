package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel:ViewModel() {
    private val userDirection = MutableLiveData<Float>()

    fun trackUserDirection(direction:Float){
        userDirection.apply {
            value = direction
        }
    }

    fun getUserDirection():MutableLiveData<Float> = userDirection
}