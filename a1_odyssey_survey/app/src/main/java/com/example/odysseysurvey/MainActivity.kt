package com.example.odysseysurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var activityState = "onCreate"
    lateinit var btnNext: Button
    lateinit var edtName: EditText
    lateinit var actxtRoles: AutoCompleteTextView
    private val roles = listOf<String>(
        "Audience", "Participant"
    )

    lateinit var attendeeName: String
    lateinit var attendedRole: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils().showLogToast(
            this,
            activityName = TAG,
            stateFrom = "non-existing",
            stateTo = activityState
        )
        setContentView(R.layout.activity_main)

        btnNext = findViewById(R.id.btn_next)
        actxtRoles = findViewById(R.id.actxt_roles)
        edtName = findViewById(R.id.edt_name)
        buildDropDownMenuItems()

    }

    private fun buildDropDownMenuItems() {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, roles
        )
        actxtRoles.setAdapter(adapter)
    }

    fun goToNextPage(view: View) {
        attendeeName = edtName.text.toString()
        attendedRole = actxtRoles.text.toString()
        val surveyPageIntent: Intent = Intent(this, SurveyActivity::class.java)
        surveyPageIntent.putExtra("name", attendeeName)
        surveyPageIntent.putExtra("role", attendedRole)
        startActivity(surveyPageIntent)
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

    override fun onSaveInstanceState(outState: Bundle) {
        attendedRole = actxtRoles.text.toString()
        attendeeName = edtName.text.toString()
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {

    }
}