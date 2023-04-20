package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel


class DirectionSensorListener(private val viewModel: HomeFragmentViewModel): SensorEventListener {

    private var currentOrientation = FloatArray(3)
    private var magneticNorthAzimuth = 0f

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR ||
            event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR){
            val rotation = FloatArray(9)

            SensorManager.getRotationMatrixFromVector(rotation, event.values)

            val orientation = FloatArray(3)
            SensorManager.getOrientation(rotation, orientation)

            val azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
            val pitch = Math.toDegrees(orientation[1].toDouble()).toFloat()
            val roll = Math.toDegrees(orientation[2].toDouble()).toFloat()

            currentOrientation[0] = azimuth
            currentOrientation[1] = pitch
            currentOrientation[2] = roll

            val diff = azimuth - magneticNorthAzimuth

            val dataDiff = "$diff data"
            //compassImageView.animate().rotation(-azimuth).start()
            viewModel.trackUserDirection(direction = -azimuth)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}