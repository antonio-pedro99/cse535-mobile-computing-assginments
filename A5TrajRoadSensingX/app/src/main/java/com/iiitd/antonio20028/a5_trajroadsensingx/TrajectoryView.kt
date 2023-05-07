package com.iiitd.antonio20028.a5_trajroadsensingx

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class TrajectoryView(context: Context, attrs: AttributeSet) : View(context, attrs){

    // Global Variables
    private var lastX = 0f
    private var lastY = 0f
    private val linePaint = Paint()
    private val dotPaint = Paint()
    private var path = Path()

    // Sensor related variables
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    init {
        linePaint.apply {
            color = Color.BLUE
            strokeWidth = 8f
            style = Paint.Style.STROKE
        }
        dotPaint.apply {
            color = Color.RED
            strokeWidth = 16f
            style = Paint.Style.FILL_AND_STROKE
        }

        // Get the sensor service and sensors
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    fun trackUserTrajectory(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> accelerometerReading.apply { System.arraycopy(event.values, 0, this, 0, size) }
            Sensor.TYPE_ROTATION_VECTOR -> magnetometerReading.apply { System.arraycopy(event.values, 0, this, 0, size) }
            Sensor.TYPE_MAGNETIC_FIELD -> magnetometerReading.apply { System.arraycopy(event.values, 0, this, 0, size) }
        }


        val acX = accelerometerReading[0]
        val acY = accelerometerReading[1]
        val acZ = accelerometerReading[2]

        val magnitude = Math.sqrt((acX * acX + acY * acY + acZ * acZ).toDouble())

        Log.d("TrajectoryView", "Magnitude: $magnitude")
        if (magnitude > 11){
            SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)

            val newX = lastX + (orientationAngles[0] * 10)
            val newY = lastY + (orientationAngles[1] * 10)

            // Add the new position to the path
            path.lineTo(newX, newY)

            lastX = newX
            lastY = newY

            Log.d("TrajectoryView", "New Position: $newX, $newY")

            // Force a redraw
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        val centerX = width / 2f
        val centerY = height / 2f
        canvas?.translate(centerX, centerY) // move the origin to the center of the canvas
        canvas?.drawPath(path, linePaint)
        canvas?.drawCircle(lastX, lastY, 8f, dotPaint)
    }
}