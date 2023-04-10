package com.iiitd.antonio20028.sensorgrama.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.iiitd.antonio20028.sensorgrama.R
import com.iiitd.antonio20028.sensorgrama.data.SensorDatabase
import com.iiitd.antonio20028.sensorgrama.data.models.GeoRotationVectorSensorModel
import com.iiitd.antonio20028.sensorgrama.data.models.LightSensorModel
import com.iiitd.antonio20028.sensorgrama.data.models.ProximitySensorModel

class HomeFragment: Fragment(), SensorEventListener {


    private lateinit var sensorManager: SensorManager
    private lateinit var sProximity:Sensor
    private lateinit var sLight :Sensor
    private var sMagneticRotationVector:Sensor? = null

    private lateinit var switchLight:Switch
    private lateinit var switchProximity:Switch
    private lateinit var switchMagneticRotationVector:Switch
    private lateinit var geoContainer:LinearLayout

    private lateinit var db:SensorDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchLight = view.findViewById(R.id.switch_light)
        switchProximity = view.findViewById(R.id.switch_proximity)
        switchMagneticRotationVector = view.findViewById(R.id.switch_magnetic_rotation_vector)
        geoContainer = view.findViewById(R.id.geo_container)

        sensorManager =  requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        sMagneticRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
        db =  SensorDatabase.getDatabaseInstance(requireContext())


        geoContainer.setOnClickListener {
            it.findNavController().navigate(R.id.geomagneticSensorFragment)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        print(event.sensor.name)
        Thread {
            when(event.sensor){
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)-> {
                    val lux = event.values[0].toFloat()
                    Log.d("S", "${event.sensor.name}: ${event.values[0].toString()}")
                    if (lux <= 5){
                        val value = LightSensorModel(illumination = lux)
                        db
                            .lightSensorTableDao().insertValue(value)
                    }
                }
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) ->{
                    Log.d("S", "${event.sensor.name}: ${event.values[0].toString()}")

                    val distance = event.values[0].toFloat()
                    if (distance <= 5){
                        val value = ProximitySensorModel(distance = distance)
                        db.proximitySensorTableDao().insertValue(value)
                    }

                }
                sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) ->
                {
                    Log.d("S", "${event.sensor.name}: x - ${event.values[0].toString()}, y - ${event.values[1].toString()}, z -${event.values[2].toString()}")
                    db.geoRotVectorTableDao().insertValue(GeoRotationVectorSensorModel(
                        yAxis = event.values[1],
                        xAxis = event.values[0],
                        zAxis = event.values[2]
                    ))
                }

            }
        }.start()

    }



    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        registerSensors()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


    private fun registerSensors(){
        switchLight.setOnCheckedChangeListener { _, isChecked ->    if (isChecked){

            sLight.also { s -> sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL) }
        } else {
            sLight.also { sensorManager.unregisterListener(this)}
        }
        }

        switchProximity.setOnCheckedChangeListener { _, isChecked ->    if (isChecked){

            sProximity.also { p-> sensorManager.registerListener(this, p, SensorManager.SENSOR_DELAY_NORMAL) }
        } else {
            sProximity.also { sensorManager.unregisterListener(this)}
        } }

        switchMagneticRotationVector.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                sMagneticRotationVector?.also { m->sensorManager.registerListener(this, m, SensorManager.SENSOR_DELAY_NORMAL) }
            } else {
                sMagneticRotationVector?.also { sensorManager.unregisterListener(this)}
            }
        }
    }
}