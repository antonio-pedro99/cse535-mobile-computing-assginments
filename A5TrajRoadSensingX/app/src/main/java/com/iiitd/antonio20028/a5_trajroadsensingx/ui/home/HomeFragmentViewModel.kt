package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class HomeFragmentViewModel:ViewModel() {
    private val userDirection = MutableLiveData<Float>()
    private val userLocation = MutableLiveData<HashMap<Any, Double>>()
    private val userSteps = MutableLiveData<Int>()

    fun trackUserDirection(direction:Float){
        userDirection.apply {
            value = direction
        }
    }

    fun countUserSteps(count: Int) = userSteps.apply { value = count }

    fun getUserDirection():MutableLiveData<Float> = userDirection
    fun getUserStepsCount() :MutableLiveData<Int> = userSteps

}