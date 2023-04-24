package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import kotlin.math.roundToInt

class StepCounterSensorListener(private val homeViewModel: HomeFragmentViewModel) :
    SensorEventListener {
    private var steps = 0
    private var lastStepTimeNs: Long = 0
    private var lastValues: FloatArray? = null
    private var lastDirections = IntArray(3)
    private var lastExtremes = FloatArray(2 * 3)
    private var lastDiff = FloatArray(3)
    private val minThreshold = 15f
    private val maxThreshold = 50f

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event?.values ?: return
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = values[0]
            val y = values[1]
            val z = values[2]

            val acceleration = Math.sqrt((x * x + y * y + z * z).toDouble())


            Log.d("A", acceleration.toString())
            val now = System.nanoTime()

            if (lastValues == null) {
                lastValues = floatArrayOf(x, y, z)
                lastStepTimeNs = now
            } else {
                val timeDelta = now - lastStepTimeNs
                if (acceleration - lastValues!!.sum() / 3 > minThreshold) {
                    lastDirections.forEachIndexed { i, _ ->
                        val diff = values[i] - lastValues!![i]
                        lastDiff[i] = diff
                        if (diff > maxThreshold && lastDirections[i] == -1) {
                            lastExtremes[i * 2] = lastValues!![i]
                            lastDirections[i] = 1
                        } else if (diff < -maxThreshold && lastDirections[i] == 1) {
                            lastExtremes[i * 2 + 1] = lastValues!![i]
                            lastDirections[i] = -1
                            if (isPeakValley(
                                    lastDirections[i],
                                    lastExtremes[i * 2],
                                    lastExtremes[i * 2 + 1]
                                )
                            ) {
                                steps++
                            }
                        }
                    }
                }

                if (timeDelta > 1000000000) {
                    lastStepTimeNs = now
                    lastValues = null
                }
            }

            homeViewModel.countUserSteps(acceleration.roundToInt())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun isPeakValley(
        direction: Int,
        lastExtremesMin: Float,
        lastExtremesMax: Float
    ): Boolean {
        val amplitude = lastExtremesMax - lastExtremesMin
        return direction == -1 && amplitude > 1.5 && amplitude < 50f
    }

    fun getSteps(): Int {
        return steps
    }

    fun reset() {
        steps = 0
        lastStepTimeNs = 0
        lastValues = null
        lastDirections.fill(0)
        lastExtremes.fill(0f)
        lastDiff.fill(0f)
    }
}