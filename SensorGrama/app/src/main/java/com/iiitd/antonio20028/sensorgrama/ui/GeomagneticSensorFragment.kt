package com.iiitd.antonio20028.sensorgrama.ui

import android.content.Context
import android.hardware.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.iiitd.antonio20028.sensorgrama.R

class GeomagneticSensorFragment:Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var rotationVectorSensor: Sensor
    private lateinit var compassImageView: ImageView
    private lateinit var feedbackText:TextView
    private var currentOrientation = FloatArray(3)
    private var magneticNorthAzimuth = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_geomagnetic_sensor_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            view.findNavController().popBackStack()
        }

        sensorManager =  requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)


        compassImageView = view.findViewById(R.id.compassImg)
        feedbackText = view.findViewById(R.id.feedbackTxt)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL)

    }

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

            val direction:String = if(diff > 0){
                "counter-clockwise"
            } else {
                "clockwise"
            }
            Log.d("V", azimuth.toString())
            val feedback = requireActivity().resources.getString(R.string.feedback, direction,
                Math.abs(diff).toString())
            feedbackText.text = feedback

            if (Math.abs(diff)  < 0.1){
                val success = "Success! Your device is now aligned with Earth's frame of reference.";
                feedbackText.text = success
            }

            compassImageView.animate().rotation(-azimuth).start()


        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}