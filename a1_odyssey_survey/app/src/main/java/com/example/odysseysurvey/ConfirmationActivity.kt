package com.example.odysseysurvey

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat.OrientationMode
import androidx.appcompat.widget.Toolbar

class ConfirmationActivity : AppCompatActivity() {
    private val TAG = "ConfirmationActivity"
    private var activityState = "onCreate"

    private lateinit var attendName: String
    private lateinit var attendRole: String
    lateinit var attendeeNameTextView: TextView
    lateinit var submissionDetailsTextView: TextView
    lateinit var eventRatingTextView: TextView
    lateinit var attendeeRoleTextView: TextView
    lateinit var layoutContainer:LinearLayout
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
        attendeeNameTextView = findViewById(R.id.confirmation_attendee_name)
        submissionDetailsTextView = findViewById(R.id.submission_details)
        eventRatingTextView = findViewById(R.id.event_raring)
        attendeeRoleTextView  = findViewById(R.id.confirmation_role)
        layoutContainer = findViewById(R.id.layout_container_3)
        attendName = intent.getStringExtra("SurveyEventAttendeeName").toString()
        attendRole = intent.getStringExtra("SurveyEventAttendeeRole").toString()
        val map = intent.getSerializableExtra("events") as MutableMap<*, *>
        attendeeNameTextView.text =
            resources.getString(R.string.confirmation_participant_name, attendName)
        attendeeRoleTextView.text = resources.getString(R.string.role, attendRole)
        val details = resources.getString(R.string.confirmation_details, map.keys.size.toString())
        submissionDetailsTextView.text = details
        var eventsAttended = StringBuilder().apply {
            map.forEach { (e, r) ->
                if (r.toString() == "0"){
                    append("$e: Not rated\n")
                } else{
                    append("$e: $r stars\n")
                }
            }
        }.toString()
        eventRatingTextView.text = eventsAttended
    }

    override fun onRestart() {
        super.onRestart()
        Utils().showLogToast(
            this,
            activityName = TAG,
            stateFrom = activityState,
            stateTo = "onRestart"
        )
        activityState = "onRestart"
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

    override fun onBackPressed() {
        val mainActivity = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mainActivity)
    }

}