package com.example.odysseysurvey

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible

class SurveyActivity : AppCompatActivity() {
    private val TAG = "SurveyActivity"

    private val danceStars = listOf<Boolean>(false, false, false, false, false)
    private val playStars = listOf<Boolean>(false, false, false, false, false)
    private val musicStars = listOf<Boolean>(false, false, false, false, false)
    private val foodStars = listOf<Boolean>(false, false, false, false, false)
    private val fashionStars = listOf<Boolean>(false, false, false, false, false)

    lateinit var danceCheckBox: CheckBox
    lateinit var playCheckBox: CheckBox
    lateinit var musicCheckBox: CheckBox
    lateinit var foodCheckBox: CheckBox
    lateinit var fashionCheckBox: CheckBox

    lateinit var danceStarsView: LinearLayout
    lateinit var musicStarsView: LinearLayout
    lateinit var playStarsView: LinearLayout
    lateinit var foodStarsView: LinearLayout
    lateinit var fashionStarsView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        Log.d(TAG, "$TAG onCreate")
        Toast.makeText(this, "$TAG onCreate", Toast.LENGTH_SHORT).show()

        initializeViews()
        setStarsVisibility()
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

    private fun setStarsVisibility() {
        musicCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                musicStarsView.visibility = View.VISIBLE
            } else {
                musicStarsView
                    .visibility = View.INVISIBLE
            }
        }
        fashionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                fashionStarsView.visibility = View.VISIBLE
            } else {
                fashionStarsView
                    .visibility = View.INVISIBLE
            }
        }

        playCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                playStarsView.visibility = View.VISIBLE
            } else {
                playStarsView
                    .visibility = View.INVISIBLE
            }
        }

        danceCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                danceStarsView.visibility = View.VISIBLE
            } else {
                danceStarsView
                    .visibility = View.INVISIBLE
            }
        }

        foodCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                foodStarsView.visibility = View.VISIBLE
            } else {
                foodStarsView
                    .visibility = View.INVISIBLE
            }
        }


    }
    private fun initializeViews(){
        foodCheckBox = findViewById(R.id.food_checkbox)
        playCheckBox = findViewById(R.id.play_checkbook)
        musicCheckBox = findViewById(R.id.music_checkbox)
        fashionCheckBox = findViewById(R.id.fasion_checkbox)
        danceCheckBox = findViewById(R.id.dance_checkbox)
        musicStarsView = findViewById(R.id.music_stars)
        foodStarsView = findViewById(R.id.food_stars)
        danceStarsView = findViewById(R.id.dance_stars)
        playStarsView = findViewById(R.id.play_stars)
        fashionStarsView = findViewById(R.id.fashion_stars)
    }
}