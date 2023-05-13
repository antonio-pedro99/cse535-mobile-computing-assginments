package com.iiitd.antonio20028.a5_trajroadsensingx.ui.home

import android.content.Context
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
import androidx.fragment.app.Fragment
import com.iiitd.antonio20028.a5_trajroadsensingx.MainActivity
import com.iiitd.antonio20028.a5_trajroadsensingx.R
import com.iiitd.antonio20028.a5_trajroadsensingx.TrajectoryView
import com.iiitd.antonio20028.a5_trajroadsensingx.ui.onboarding.OnBoardingFragmentViewModel
import com.iiitd.antonio20028.a5_trajroadsensingx.utils.UserMovementState
import com.iiitd.antonio20028.a5_trajroadsensingx.utils.changeStatusBarColor
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.abs
import kotlin.math.sqrt


private const val REQUEST_LOCATION_PERMISSION = 1

class HomeFragment : Fragment() {

    private var phoneMagneticField = FloatArray(9)
    private lateinit var mySensorEventListener: MySensorEventListener
    private lateinit var sensorManager: SensorManager
    private lateinit var rotationVectorSensor: Sensor
    private lateinit var accelerometerSensor: Sensor
    private lateinit var userInformationViewModel: OnBoardingFragmentViewModel
    lateinit var linearLayoutWalking: LinearLayout
    private var stepsCount: Int = 0
    private var steps = AtomicInteger(0)
    lateinit var tvSteps: TextView
    lateinit var tvDistance: TextView
    lateinit var imgDirection: ImageView
    private var earthReferenceGravity = FloatArray(9)
    lateinit var txtUserIsWalking: TextView
    lateinit var txtUserInLift: TextView
    lateinit var txtUserInStairs: TextView
    lateinit var imgUserWalking: ImageView
    lateinit var imgUserInLift: ImageView
    lateinit var imgUserInStairs: ImageView
    lateinit var backgroundLayoutUserWalking: LinearLayout
    lateinit var backgroundLayoutUserInLift: LinearLayout
    lateinit var backgroundLayoutUserInStairs: LinearLayout
    private var stepsIsCounting = false
    lateinit var values: TextView
    lateinit var txtVelocity: TextView
    lateinit var trajectoryView: TrajectoryView

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
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        rotationVectorSensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
                ?: sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        userInformationViewModel = (activity as MainActivity).getViewModel()

        tvSteps.text = stepsCount.toString()

    }


    private val handleSensorEventListener = object : SensorEventListener {

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                handleAccelerometer(event)

                //Log.d("HomeFragment", "Accelerometer data: ${event.values[0]}, ${event.values[1]}, ${event.values[2]}")

            } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD || event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
                handleMagneticField(event)
                //Log.d("HomeFragment", "Magnetic field data: ${event.values[0]}, ${event.values[1]}, ${event.values[2]}")
            }
            trajectoryView.trackUserTrajectory(event)
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }


    private fun handleAccelerometer(event: SensorEvent) {
        earthReferenceGravity = event.values.clone()
        trackSteps(event)
        trackUserMovementState(event)
    }

    private fun handleMagneticField(event: SensorEvent) {
        phoneMagneticField = event.values.clone()
        trackDeviceDirection(event)
    }

    private var magnitudeDelta = 0.0
    private val magnitudeStepThreshold = 2.5
    private var strideFrequency = 0.0
    private var strideTime = 0.0
    private fun trackSteps(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val magnitude = Math.sqrt((x * x + y * y + z * z).toDouble())

        if (magnitudeDelta == 0.0) {
            magnitudeDelta = magnitude
        } else {
            val delta = magnitude - magnitudeDelta
            magnitudeDelta = magnitude

            stepsIsCounting = if (delta > magnitudeStepThreshold) {
                steps.incrementAndGet()
                true
            } else {
                false
            }

            tvSteps.text = steps.get().toString()

            val strideLength = userInformationViewModel.getStrideLength().value!!

            val distance = (steps.get() * strideLength).toFloat() / 100000f

            tvDistance.text = String.format("%.3f", distance)

            strideTime = delta.toDouble() / 1000000000.0 // convert from nanoseconds to seconds
            strideFrequency = 1.0 / strideTime

            val velocity = distance / strideTime

            // Display the velocity on the screen
            txtVelocity.text = String.format("%.2f", abs(velocity))

        }
    }


    private var azimut = 0.0
    private fun trackDeviceDirection(event: SensorEvent) {
        val rotation = FloatArray(9)
        val inclination = FloatArray(9)

        val deviceNotFreeFailing = SensorManager.getRotationMatrix(
            rotation,
            inclination,
            earthReferenceGravity,
            phoneMagneticField
        )

        if (deviceNotFreeFailing) {
            val orientation = FloatArray(3)
            SensorManager.getOrientation(rotation, orientation)

            azimut = orientation[0].toDouble()

            val degree = (Math.toDegrees(azimut)).toFloat()

            imgDirection.rotation = degree
        }
    }

    private var movingTimer: Runnable? = null
    private val handler = Handler(Looper.getMainLooper())
    private var state = UserMovementState.STATIONARY
    private var lastStateChangeTime = System.currentTimeMillis()
    private fun trackUserMovementState(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val magnitude = sqrt((x * x + y * y + z * z).toDouble())
        Log.d("HomeFragment", "Magnitude: $magnitude")


        if (z > 9 && y < 8) {

            state = if (z >  10 && z < 12 && x <= 1) {
                UserMovementState.WALKING
            } else if (y <= 0 && x < 1 && z < 9.5 ){
                UserMovementState.STATIONARY
            } else if (z > 13 && y < 1){
                UserMovementState.TAKING_STAIRS
            } else if((y > -0.1 && y < 0.1) && (x > -0.1 && x < 0.1)) {
                UserMovementState.TAKING_ELEVATOR
            } else{
                UserMovementState.UNKNOWN
            }
        }
        updateWalkingUI(state)
        values.text = "X: $x\nY: $y\n Z: $z\nState: $state"

    }


    private fun updateWalkingUI(userMovementState: UserMovementState) {
        Log.d("HomeFragment", "Counting: $stepsIsCounting")
        if (stepsIsCounting) {
            backgroundLayoutUserWalking.setBackgroundResource(R.drawable.btn_background_selected)
            txtUserIsWalking.setTextColor(resources.getColor(R.color.black))
            imgUserWalking.setImageDrawable(activity?.resources?.getDrawable(R.drawable.baseline_directions_walk_black_24))
            backgroundLayoutUserWalking.postDelayed(Runnable { }, 1000)
        } else if (userMovementState == UserMovementState.TAKING_STAIRS) {
            backgroundLayoutUserInStairs.setBackgroundResource(R.drawable.btn_background_selected)
            txtUserInStairs.setTextColor(resources.getColor(R.color.black))
            imgUserInStairs.setImageDrawable(activity?.resources?.getDrawable(R.drawable.outline_elevator_black_24))
        } else if (userMovementState == UserMovementState.TAKING_ELEVATOR) {
            backgroundLayoutUserInLift.setBackgroundResource(R.drawable.btn_background_selected)
            txtUserInLift.setTextColor(resources.getColor(R.color.black))
            imgUserInLift.setImageDrawable(activity?.resources?.getDrawable(R.drawable.outline_elevator_black_24))
        } else if (userMovementState == UserMovementState.STATIONARY || userMovementState == UserMovementState.UNKNOWN) {
            //walking unselected
            backgroundLayoutUserWalking.setBackgroundResource(R.drawable.btn_background_unselected)
            txtUserIsWalking.setTextColor(resources.getColor(R.color.white))
            imgUserWalking.setImageDrawable(activity?.resources?.getDrawable(R.drawable.baseline_directions_walk_24))
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

        sensorManager.registerListener(
            handleSensorEventListener,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            handleSensorEventListener,
            rotationVectorSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

    }


    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(movingTimer!!)
        sensorManager.unregisterListener(mySensorEventListener)
    }


    private fun initViews(view: View) {
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
        txtVelocity = view.findViewById(R.id.txt_velocity)
        trajectoryView = view.findViewById(R.id.trajectoryView)
    }

}