package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iiitd.antonio20028.a5_trajroadsensingx.MainActivity
import com.iiitd.antonio20028.a5_trajroadsensingx.R
import com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding.OnBoardingFragmentViewModel
import com.iiitd.antonio20028.a5_trajroadsensingx.utils.UserMovementState
import com.iiitd.antonio20028.a5_trajroadsensingx.utils.changeStatusBarColor
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.sqrt


private const val REQUEST_LOCATION_PERMISSION = 1
class HomeFragment : Fragment() {

    private var phoneMagneticField = FloatArray(9)
    private lateinit var mySensorEventListener: MySensorEventListener
    private lateinit var sensorManager: SensorManager
    private lateinit var rotationVectorSensor: Sensor
    private lateinit var accelerometerSensor: Sensor
    private lateinit var userInformationViewModel: OnBoardingFragmentViewModel
    lateinit var linearLayoutWalking : LinearLayout
    private var stepsCount:Int = 0
    private var steps = AtomicInteger(0)
    lateinit var tvSteps: TextView
    lateinit var tvDistance: TextView
    lateinit var imgDirection: ImageView
    private var earthReferenceGravity = FloatArray(9)
    lateinit var txtUserIsWalking: TextView
    lateinit var txtUserInLift:TextView
    lateinit var txtUserInStairs:TextView
    lateinit var imgUserWalking:ImageView
    lateinit var imgUserInLift:ImageView
    lateinit var imgUserInStairs:ImageView
    lateinit var backgroundLayoutUserWalking:LinearLayout
    lateinit var backgroundLayoutUserInLift:LinearLayout
    lateinit var backgroundLayoutUserInStairs:LinearLayout
    private var stepsIsCounting = false
    lateinit var values:TextView

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

        initViews(view)
        sensorManager =  requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        userInformationViewModel = (activity as MainActivity).getViewModel()

        tvSteps.text = stepsCount.toString()

    }


    private val handleSensorEventListener = object : SensorEventListener {

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
                handleAccelerometer(event)

                //Log.d("HomeFragment", "Accelerometer data: ${event.values[0]}, ${event.values[1]}, ${event.values[2]}")

            } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD || event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR){
                handleMagneticField(event)
                //Log.d("HomeFragment", "Magnetic field data: ${event.values[0]}, ${event.values[1]}, ${event.values[2]}")
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }


    private fun handleAccelerometer(event: SensorEvent){
        earthReferenceGravity = event.values.clone()
        trackSteps(event)
        trackUserMovementState(event)
    }
    private fun handleMagneticField(event: SensorEvent){
        phoneMagneticField = event.values.clone()
        trackDeviceDirection(event)
    }

    private var magnitudeDelta = 0.0
    private val magnitudeStepThreshold = 2.5
    private fun trackSteps(event: SensorEvent){
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val magnitude = Math.sqrt((x*x + y*y + z*z).toDouble())

        if (magnitudeDelta == 0.0){
            magnitudeDelta = magnitude
        } else {
            val delta = magnitude - magnitudeDelta
            magnitudeDelta = magnitude

            stepsIsCounting = if (delta > magnitudeStepThreshold){
                steps.incrementAndGet()
                stepsCount++
                true
            } else {
                false
            }

            tvSteps.text = steps.get().toString()

            val strideLength = userInformationViewModel.getStrideLength().value!!

            val distance = (steps.get() * strideLength).toFloat() / 100000f
            values.text = "Magnitude: $magnitude\nDelta: $delta\nX: $x\nY: $y\n Z: $z\nState: $state"
            tvDistance.text = String.format("%.4f", distance)
        }
    }


    private var azimut = 0.0
    private fun trackDeviceDirection(event: SensorEvent){
        val rotation = FloatArray(9)
        val inclination = FloatArray(9)

        val deviceNotFreeFailing = SensorManager.getRotationMatrix(rotation, inclination, earthReferenceGravity, phoneMagneticField)

        if (deviceNotFreeFailing){
            val orientation = FloatArray(3)
            SensorManager.getOrientation(rotation, orientation)

            azimut = orientation[0].toDouble()

            val degree = (Math.toDegrees(azimut)).toFloat()

            imgDirection.rotation = -degree
        }
    }

    private var movingTimer: Runnable? = null
    private val handler = Handler(Looper.getMainLooper())
    private var state = UserMovementState.STATIONARY
    private fun trackUserMovementState(event: SensorEvent){
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val magnitude = sqrt((x*x + y*y + z*z).toDouble())
        Log.d("HomeFragment", "Magnitude: $magnitude")
        movingTimer = Runnable {
            state  =  when(magnitude){
                in 0.0..11.0 -> UserMovementState.STATIONARY
                in 11.0..13.0 -> UserMovementState.WALKING
                else -> {
                    Log.d("HomeFragment", "Z: $z")
                    if (z > -.5 && z < .6){
                        UserMovementState.WALKING
                    } else {
                        UserMovementState.STATIONARY
                    }
                }}
            handler.postDelayed(movingTimer!!, 1000)
        }
        handler.postDelayed(movingTimer!!, 1000)

        updateWalkingUI(state)
    }

    private fun updateWalkingUI( userMovementState: UserMovementState){
        Log.d("HomeFragment", "Counting: $stepsIsCounting")
        if (userMovementState == UserMovementState.WALKING || stepsIsCounting){
            backgroundLayoutUserWalking.setBackgroundResource(R.drawable.btn_background_selected)
            txtUserIsWalking.setTextColor(resources.getColor(R.color.black))
            imgUserWalking .setImageDrawable(activity?.resources?.getDrawable(R.drawable.baseline_directions_walk_black_24))
            backgroundLayoutUserWalking.postDelayed(Runnable {  }, 1000)
        } else if (userMovementState == UserMovementState.TAKING_STAIRS) {
            backgroundLayoutUserInStairs.setBackgroundResource(R.drawable.btn_background_selected)
            txtUserInStairs.setTextColor(resources.getColor(R.color.black))
            imgUserInStairs.setImageDrawable(activity?.resources?.getDrawable(R.drawable.outline_elevator_black_24))
        } else if(userMovementState == UserMovementState.TAKING_ELEVATOR){
            backgroundLayoutUserInLift.setBackgroundResource(R.drawable.btn_background_selected)
            txtUserInLift.setTextColor(resources.getColor(R.color.black))
            imgUserInLift.setImageDrawable(activity?.resources?.getDrawable(R.drawable.outline_elevator_black_24))
        } else if (userMovementState == UserMovementState.STATIONARY){
            //walking unselected
            backgroundLayoutUserWalking.setBackgroundResource(R.drawable.btn_background_unselected)
            txtUserIsWalking.setTextColor(resources.getColor(R.color.white))
            imgUserWalking .setImageDrawable(activity?.resources?.getDrawable(R.drawable.baseline_directions_walk_24))
            //in lift unselected
            backgroundLayoutUserInLift.setBackgroundResource(R.drawable.btn_background_unselected)
            txtUserInLift.setTextColor(resources.getColor(R.color.white))
            imgUserInLift.setImageDrawable(activity?.resources?.getDrawable(R.drawable.outline_elevator_24))
            //taking stairs unselected
            backgroundLayoutUserInStairs.setBackgroundResource(R.drawable.btn_background_unselected)
            txtUserInStairs.setTextColor(resources.getColor(R.color.white))
            imgUserInStairs.setImageDrawable(activity?.resources?.getDrawable(R.drawable.outline_stairs_24))

        }
    }


    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(handleSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(handleSensorEventListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(movingTimer!!)
        sensorManager.unregisterListener(mySensorEventListener)
    }


    private fun initViews(view:View){
        backgroundLayoutUserWalking = view.findViewById(R.id.ll_walking)
        tvSteps = view.findViewById(R.id.txt_steps)
        tvDistance = view.findViewById(R.id.txt_distance)
        imgDirection = view.findViewById(R.id.img_compass)
        txtUserInLift = view.findViewById(R.id.txt_user_in_lift)
        txtUserInStairs = view.findViewById(R.id.txt_user_in_stairs)
        txtUserIsWalking = view.findViewById(R.id.txt_walking)
        backgroundLayoutUserInLift = view.findViewById(R.id.ll_elevator)
        backgroundLayoutUserInStairs = view.findViewById(R.id.ll_stairs)
        imgUserInLift = view.findViewById(R.id.img_lift)
        imgUserInStairs = view.findViewById(R.id.img_stairs)
        imgUserWalking = view.findViewById(R.id.img_walking)
        values = view.findViewById(R.id.txtValues)
    }

}