package com.example.odysseysurvey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        Toast.makeText(this, "SurveyActivity onCreate", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "SurveyActivity onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "SurveyActivity onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "SurveyActivity onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "SurveyActivity onDestroy", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "SurveyActivity onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Toast.makeText(this, "SurveyActivity onPause", Toast.LENGTH_SHORT).show()
        super.onPause()
    }
}