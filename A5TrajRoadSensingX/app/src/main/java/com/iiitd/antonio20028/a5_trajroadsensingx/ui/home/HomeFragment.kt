package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.Manifest
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.iiitd.antonio20028.a5_trajroadsensingx.MainActivity
import com.iiitd.antonio20028.a5_trajroadsensingx.R
import com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding.OnBoardingFragmentViewModel
import com.iiitd.antonio20028.a5_trajroadsensingx.utils.changeStatusBarColor
private const val REQUEST_LOCATION_PERMISSION = 1
class HomeFragment : Fragment() {

    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private lateinit var directionSensorListener: DirectionSensorListener
    private lateinit var mySensorEventListener: MySensorEventListener
    private lateinit var stepCounterSensorListener: StepCounterSensorListener
    private lateinit var sensorManager: SensorManager
    private lateinit var rotationVectorSensor: Sensor
    private lateinit var accelerometerSensor: Sensor
    private lateinit var locationViewModel: LocationViewModel
    private var currentMovementState = UserMovementState.STATIONARY
    lateinit var linearLayoutWalking : LinearLayout


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

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        checkLocationPermission()
        val directionImageView = view.findViewById<ImageView>(R.id.img_compass)
        linearLayoutWalking = view.findViewById<LinearLayout>(R.id.ll_walking)

        val distanceTextView = view.findViewById<TextView>(R.id.txt_distance)


        sensorManager =  requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        val userInfoViewModel = (activity as MainActivity).getViewModel()
        homeFragmentViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)


        mySensorEventListener = MySensorEventListener(homeFragmentViewModel, currentMovementState, userInfoViewModel.getStrideLength().value!!)

        homeFragmentViewModel.getUserDirection().observe(viewLifecycleOwner){
            directionImageView.animate().rotation(it).start()
        }



        locationViewModel.location.observe(viewLifecycleOwner){
            //distanceTextView.text = it.toString()
            Log.d("D", "Lat ${it.latitude}, Lon ${it.longitude}")
        }


        homeFragmentViewModel.getUserStepsCount().observe(viewLifecycleOwner){
            val txtCount:TextView = view.findViewById(R.id.txt_steps)
            txtCount.text = it.toString()
        }
        homeFragmentViewModel.getPhoneAcceleration().observe(viewLifecycleOwner){

            when (it!!){
                UserMovementState.STATIONARY -> {

                    updateWalkingUI(view)
                }
                UserMovementState.WALKING -> {
                   updateWalkingUI(view)
                }

                UserMovementState.TAKING_ELEVATOR -> {

                }
                UserMovementState.TAKING_STAIRS -> {

                }
            }

          //  Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            currentMovementState    = it
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(mySensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(mySensorEventListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationViewModel.stopLocationUpdates()
        sensorManager.unregisterListener(mySensorEventListener)
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       when(requestCode){
           REQUEST_LOCATION_PERMISSION ->{
               if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   locationViewModel.startLocationUpdates(requireContext())
               } else {
                   Toast.makeText(requireContext(), "You need to grant location permission", Toast.LENGTH_LONG).show()
               }
           }
       }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION)
        } else {
           locationViewModel.startLocationUpdates(requireContext())
        }
    }


    private fun updateWalkingUI(view: View){
       if (currentMovementState == UserMovementState.WALKING){
           linearLayoutWalking.setBackgroundResource(R.drawable.btn_background_selected)
           view.findViewById<TextView?>(R.id.txt_walking).also {
               it?.setTextColor(resources.getColor(R.color.black))
           }
           view.findViewById<ImageView>(R.id.img_walking).also {
               it.setImageDrawable(resources.getDrawable(R.drawable.baseline_directions_walk_black_24))
           }
       } else if (currentMovementState == UserMovementState.STATIONARY){
           linearLayoutWalking.setBackgroundResource(R.drawable.btn_background_unselected)
           view.findViewById<TextView?>(R.id.txt_walking).also {
               it?.setTextColor(resources.getColor(R.color.white))
           }
           view.findViewById<ImageView>(R.id.img_walking).also {
               it.setImageDrawable(resources.getDrawable(R.drawable.baseline_directions_walk_24))
           }
       }
    }

}