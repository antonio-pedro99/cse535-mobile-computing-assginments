package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
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
    private val userMovement = MutableLiveData<UserMovementState>()
    private var movingTimer: Runnable? = null
    private val handler = Handler(Looper.getMainLooper())

    fun trackUserDirection(direction:Float){
        userDirection.apply {
            value = direction
        }
    }

    fun trackPhoneAcceleration(acceleration:Float){
        movingTimer = Runnable {
            userMovement.apply {
                value = when(acceleration){
                    in 0.0..0.5 -> UserMovementState.STATIONARY
                    in 0.5..1.5 -> UserMovementState.WALKING
                    in 1.5..2.5 -> UserMovementState.TAKING_STAIRS
                    else -> UserMovementState.TAKING_ELEVATOR
                }
            }
            handler.postDelayed(movingTimer!!, 1000)
        }
        handler.postDelayed(movingTimer!!, 1000)

    }
    fun countUserSteps(count: Int) = userSteps.apply { value = count }

    fun getUserDirection():MutableLiveData<Float> = userDirection
    fun getUserStepsCount() :MutableLiveData<Int> = userSteps
    fun getPhoneMovement():MutableLiveData<UserMovementState> = userMovement

    fun stopMoveTimer(){
        handler.removeCallbacks(movingTimer!!)
    }

    private fun updateMovingState(state: UserMovementState){
        userMovement.value = state
    }
}

enum class UserMovementState{
    STATIONARY,
    WALKING,
    TAKING_STAIRS,
    TAKING_ELEVATOR
}