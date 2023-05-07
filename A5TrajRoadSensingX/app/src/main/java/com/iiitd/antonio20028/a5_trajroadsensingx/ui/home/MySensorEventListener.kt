package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.roundToInt

class MySensorEventListener(private val strideLength:Double): SensorEventListener {

    private var currentOrientation = FloatArray(3)
    private var magneticNorthAzimuth = 0f

    private var stepCount = 0
    private var lastStepTime = System.currentTimeMillis()

    private val alpha = 0.8f // Accelerometer low-pass filter constant
    private var gravity = floatArrayOf(0f, 0f, 0f)
    private var linearAcceleration = floatArrayOf(0f, 0f, 0f)
    private var magnetometer = floatArrayOf(0f, 0f, 0f)
    private var lastDisplacement = 0.0

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event?.values ?: return
        if (event.sensor?.type == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR ||
            event.sensor?.type == Sensor.TYPE_ROTATION_VECTOR || event.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD){
            handleMagneticRotationVector(event)
        } else if (event.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            handleAccelerometer(event)
        }

    }

    private fun isPeakValley(
        direction: Int,
        lastExtremesMin: Float,
        lastExtremesMax: Float
    ): Boolean {
        val amplitude = lastExtremesMax - lastExtremesMin
        return direction == -1 && amplitude > 1.5 && amplitude < 50f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun handleAccelerometer(event: SensorEvent){
        // Apply low-pass filter to accelerometer data
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

        // Remove gravity component from accelerometer data to get linear acceleration
        linearAcceleration[0] = event.values[0] - gravity[0]
        linearAcceleration[1] = event.values[1] - gravity[1]
        linearAcceleration[2] = event.values[2] - gravity[2]

        // Detect step using linear acceleration data
        val stepDetected = detectStep(linearAcceleration)

        // Update step count if a step is detected
        if (stepDetected) {
            val now = System.currentTimeMillis()
            if (now - lastStepTime > STEP_INTERVAL) {
                stepCount++
                lastStepTime = now
            }
        }

        // compute absolute acceleration
        val aX = linearAcceleration[0]
        val aY = linearAcceleration[1]
        val aZ = linearAcceleration[2]
        val aAbs = Math.sqrt((aX*aX + aY*aY + aZ*aZ).toDouble())


        Log.d("L", aAbs.toString())

    }

    private fun handleMagneticRotationVector(event: SensorEvent){
        val rotation = FloatArray(9)
        System.arraycopy(event.values, 0, magnetometer, 0, 3)
        SensorManager.getRotationMatrixFromVector(rotation, event.values)

        val orientation = FloatArray(3)
        SensorManager.getOrientation(rotation, orientation)

        val azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
        val pitch = Math.toDegrees(orientation[1].toDouble()).toFloat()
        val roll = Math.toDegrees(orientation[2].toDouble()).toFloat()


     /*   currentOrientation[0] = azimuth
        currentOrientation[1] = pitch
        currentOrientation[2] = roll*/

    }
    private fun detectStep(acceleration: FloatArray): Boolean {
        // Compute the magnitude of the acceleration vector
        val magnitude = Math.sqrt(acceleration[0].toDouble()*acceleration[0].toDouble() + acceleration[1].toDouble()*acceleration[1].toDouble() + acceleration[2].toDouble()*acceleration[2].toDouble()).toFloat()

        // Check if the magnitude of the acceleration vector exceeds the step threshold

        val strideThreshold = STRIDE_FACTOR * strideLength
        Log.d("MAG"," Magnetudi: ${magnitude.toString()}\n Stride: ${strideThreshold.toString()}")
        return magnitude > strideThreshold
    }

    companion object {
        private const val STEP_INTERVAL = 500 // Minimum time interval between steps (in milliseconds)
        private const val STRIDE_FACTOR = 4 // Stride factor used to estimate step length
    }
}