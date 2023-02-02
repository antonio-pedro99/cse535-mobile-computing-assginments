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
    private   val TAG = "MainActivity"
    lateinit var btnNext: Button
    lateinit var edtName: EditText
    lateinit var actxtRoles: AutoCompleteTextView
    private val roles = listOf<String>(
        "Audience", "Participant"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNext = findViewById(R.id.btn_next)
        actxtRoles = findViewById(R.id.actxt_roles)
        edtName = findViewById(R.id.edt_name)
        buildDropDownMenuItems()
        Log.d(TAG, "$TAG onCreate")
        Toast.makeText(this, "$TAG onCreate", Toast.LENGTH_SHORT).show()

    }

    private fun buildDropDownMenuItems() {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, roles
        )
        actxtRoles.setAdapter(adapter)
    }

    fun goToNextPage(view:View) {
        val surveyPageIntent:Intent = Intent(this, SurveyActivity::class.java)
        startActivity(surveyPageIntent)

        //Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "$TAG onRestart")
        Toast.makeText(this, "MainActivity onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "$TAG onStart")
        Toast.makeText(this, "MainActivity onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "MainActivity onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "$TAG onDestroy")
        Toast.makeText(this, "$TAG onDestroy", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "$TAG onResume")
        Toast.makeText(this, "$TAG onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Log.d(TAG, "$TAG onPause")
        Toast.makeText(this, "$TAG onPause", Toast.LENGTH_SHORT).show()
        super.onPause()
    }
}