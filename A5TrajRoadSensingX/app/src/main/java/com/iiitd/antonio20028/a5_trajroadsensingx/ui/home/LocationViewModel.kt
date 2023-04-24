package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.*

class LocationViewModel : ViewModel() {

    // Define a LocationRequest with the desired location update settings
    private val locationRequest = LocationRequest.create().apply {
        interval = 10000 // Update location every 10 seconds
        fastestInterval = 5000 // Use the fastest interval available
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY // Use high accuracy for best results
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location>
        get() = _location

    fun startLocationUpdates(context:Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            p0.lastLocation?.let { location ->
                _location.value = location
            }
        }
    }
}
