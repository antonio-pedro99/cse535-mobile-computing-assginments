package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
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
    private val phoneAcceleration = MutableLiveData<UserMovementState>()

    fun trackUserDirection(direction:Float){
        userDirection.apply {
            value = direction
        }
    }


    fun trackPhoneAcceleration(acceleration:FloatArray, state: UserMovementState){
        var  lastAcceleration = 0f
        var lastStateChangeTime = 0L
        var currentState = state

        phoneAcceleration.apply {
            var peak = Math.max(Math.abs(acceleration[0]), Math.abs(acceleration[1]))
            peak     = Math.max(peak, Math.abs(acceleration[2]))

            lastAcceleration = Math.sqrt(acceleration[0].toDouble()*acceleration[0].toDouble() + acceleration[1].toDouble()*acceleration[1].toDouble() + acceleration[2].toDouble()*acceleration[2].toDouble()).toFloat()
            val currentTime = System.currentTimeMillis()

            Log.d("ACC", lastAcceleration.toString())

            when(currentState){
                UserMovementState.TAKING_ELEVATOR -> {

                }
                UserMovementState.STATIONARY -> {
                    if (lastAcceleration > 10f){
                        currentState = UserMovementState.WALKING
                        lastStateChangeTime = currentTime
                    }
                }
                UserMovementState.TAKING_STAIRS -> {
                    if (lastAcceleration < 15f){
                        currentState = UserMovementState.WALKING
                        lastStateChangeTime = currentTime
                    }
                }
                UserMovementState.WALKING -> {
                   if (lastAcceleration < 10f){
                        currentState = UserMovementState.STATIONARY
                        lastStateChangeTime = currentTime
                   } else if (lastAcceleration > 15f){
                        currentState = UserMovementState.TAKING_STAIRS
                       lastStateChangeTime = currentTime
                   }
                }
            }
            if (currentState == UserMovementState.STATIONARY || currentState == UserMovementState.WALKING){

                val stateDuration = currentTime - lastStateChangeTime
                if (stateDuration < 10000){
                    currentState = UserMovementState.STATIONARY
                    Log.d("STA", lastAcceleration.toString())
                }
            }
            value = currentState
        }

    }
    fun countUserSteps(count: Int) = userSteps.apply { value = count }

    fun getUserDirection():MutableLiveData<Float> = userDirection
    fun getUserStepsCount() :MutableLiveData<Int> = userSteps
    fun getPhoneAcceleration():MutableLiveData<UserMovementState> = phoneAcceleration

}

enum class UserMovementState{
    STATIONARY,
    WALKING,
    TAKING_STAIRS,
    TAKING_ELEVATOR
}