package com.example.odysseysurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
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

        //Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "MainActivity onRestart", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "MainActivity onStart", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "MainActivity onStop", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "MainActivity onDestroy", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "MainActivity onResume", Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        Toast.makeText(this, "MainActivity onPause", Toast.LENGTH_LONG).show()
        super.onPause()
    }
}