package com.example.odysseysurvey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class ConfirmationActivity : AppCompatActivity() {
    private val TAG = "ConfirmationActivity"
    private var activityState = "onCreate"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils().showLogToast(
            this,
            activityName = TAG,
            stateFrom = "non-existing",
            stateTo = activityState
        )
        setContentView(R.layout.activity_confirmation)
        supportActionBar?.hide()

    }

    override fun onRestart() {
        super.onRestart()
        val message = "$TAG change from $activityState to onStop"
        Log.d(TAG, message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        activityState = "onStop"
    }

    override fun onStart() {
        super.onStart()
        Utils().showLogToast(
            this,
            activityName = TAG,
            stateFrom = activityState,
            stateTo = "onStart"
        )
        activityState = "onStart"
    }

    override fun onStop() {
        super.onStop()
        Utils().showLogToast(
            this,
            activityName = TAG,
            stateFrom = activityState,
            stateTo = "onStop"
        )
        activityState = "onStop"
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils().showLogToast(
            this,
            activityName = TAG,
            stateFrom = activityState,
            stateTo = "onDestroy"
        )
        activityState = "onDestroy"

    }

    override fun onResume() {
        super.onResume()
        Utils().showLogToast(
            this,
            activityName = TAG,
            stateFrom = activityState,
            stateTo = "onResume"
        )
        activityState = "onResume"
    }

    override fun onPause() {
        super.onPause()
        Utils().showLogToast(
            this,
            activityName = TAG,
            stateFrom = activityState,
            stateTo = "onPause"
        )
        activityState = "onPause"
    }


}