package com.example.odysseysurvey

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class SurveyActivity : AppCompatActivity() {
    private val TAG = "SurveyActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        Log.d(TAG, "$TAG onCreate")
        Toast.makeText(this, "$TAG onCreate", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "$TAG onRestart")
        Toast.makeText(this, "$TAG onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "$TAG onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "$TAG onStop")
        Toast.makeText(this, "$TAG onStop", Toast.LENGTH_SHORT).show()
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