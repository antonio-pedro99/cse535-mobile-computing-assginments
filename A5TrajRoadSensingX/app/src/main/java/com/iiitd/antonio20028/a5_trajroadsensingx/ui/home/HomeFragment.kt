package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iiitd.antonio20028.a5_trajroadsensingx.R
import com.iiitd.antonio20028.a5_trajroadsensingx.utils.changeStatusBarColor

class HomeFragment : Fragment() {

    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private lateinit var directionSensorListener: DirectionSensorListener
    private lateinit var sensorManager: SensorManager
    private lateinit var rotationVectorSensor: Sensor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.changeStatusBarColor(
            statusBarColor = activity?.resources?.getColor(R.color.green),
            navigationBarColor = activity?.resources?.getColor(
                R.color.white
            ),
            true
        )
        val directionImageView = view.findViewById<ImageView>(R.id.img_compass)

        //get geomagnetic or rotation vector
        sensorManager =  requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        homeFragmentViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        directionSensorListener = DirectionSensorListener(homeFragmentViewModel)

        homeFragmentViewModel.getUserDirection().observe(viewLifecycleOwner){
            directionImageView.animate().rotation(it).start()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(directionSensorListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(directionSensorListener)
    }


}