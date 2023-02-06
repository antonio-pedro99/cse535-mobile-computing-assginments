package com.example.odysseysurvey

import android.media.Image
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
const val MUSIC_RATE = "music"
const val TAG = "SurveyActivity"

class SurveyActivity : AppCompatActivity() {



    private val danceStarsValue = mutableListOf<Boolean>(false, false, false, false, false)
    private val playStarsValue = mutableListOf<Boolean>(false, false, false, false, false)
    private var musicStarsValue = mutableListOf<Boolean>(false, false, false, false, false)
    private val foodStarsValue = mutableListOf<Boolean>(false, false, false, false, false)
    private val fashionStarsValue = mutableListOf<Boolean>(false, false, false, false, false)

    lateinit var danceCheckBox: CheckBox
    private lateinit var playCheckBox: CheckBox
    private lateinit var musicCheckBox: CheckBox
    private lateinit var foodCheckBox: CheckBox
    lateinit var fashionCheckBox: CheckBox
    private lateinit var saluteTextView: TextView

    //linear layout views
    lateinit var danceStarsView: LinearLayout
    lateinit var musicStarsView: LinearLayout
    lateinit var playStarsView: LinearLayout
    lateinit var foodStarsView: LinearLayout
    lateinit var fashionStarsView: LinearLayout

    //stars views
    private var musicStars: MutableList<ImageView> = mutableListOf()
    private var foodStars: MutableList<ImageView> = mutableListOf()
    private var fashionStars: MutableList<ImageView> = mutableListOf()
    private var playStars: MutableList<ImageView> = mutableListOf()
    private var danceStars: MutableList<ImageView> = mutableListOf()

    lateinit var attendName: String
    lateinit var attendRole: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        Log.d(TAG, "$TAG onCreate")
        Toast.makeText(this, "$TAG onCreate", Toast.LENGTH_SHORT).show()
        saluteTextView = findViewById(R.id.salute)
        if (savedInstanceState != null) {
            musicStarsValue = savedInstanceState.getBooleanArray(MUSIC_RATE)?.toMutableList()!!

        }
        //val saluteResource = resources.getString(R.string.option_event)
        //saluteTextView.text = String.format(saluteResource, attendName)
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

    private fun initializeViews() {
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
        initMusicStarsView()
        initDanceStarsView()
        initFashionStarsView()
        initFoodStarsView()
        initPlayStarsView()
    }

    private fun initMusicStarsView() {
        musicStars.add(findViewById(R.id.m_star_1))
        musicStars.add(findViewById(R.id.m_star_2))
        musicStars.add(findViewById(R.id.m_star_3))
        musicStars.add(findViewById(R.id.m_star_4))
        musicStars.add(findViewById(R.id.m_star_5))

        musicStars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                autoFillStarRight(musicStarsValue, index, !musicStarsValue[index])
                star(values = musicStarsValue, views = musicStars)
                Toast.makeText(
                    this,
                    "Music rate: ${musicStarsValue.filter { it }.size.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initFashionStarsView() {
        fashionStars.add(findViewById(R.id.fo_star_1))
        fashionStars.add(findViewById(R.id.fo_star_2))
        fashionStars.add(findViewById(R.id.fo_star_3))
        fashionStars.add(findViewById(R.id.fo_star_4))
        fashionStars.add(findViewById(R.id.fo_star_5))

        fashionStars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                autoFillStarRight(fashionStarsValue, index, !fashionStarsValue[index])
                star(values = fashionStarsValue, views = fashionStars)
                Toast.makeText(
                    this,
                    "Fashion rate: ${fashionStarsValue.filter { it }.size}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initFoodStarsView() {
        foodStars.add(findViewById(R.id.f_star_1))
        foodStars.add(findViewById(R.id.f_star_2))
        foodStars.add(findViewById(R.id.f_star_3))
        foodStars.add(findViewById(R.id.f_star_4))
        foodStars.add(findViewById(R.id.f_star_5))

        foodStars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                autoFillStarRight(foodStarsValue, index, !foodStarsValue[index])
                star(values = foodStarsValue, views = foodStars)
                Toast.makeText(
                    this,
                    "Food rate: ${foodStarsValue.filter { it }.size.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initPlayStarsView() {
        playStars.add(findViewById(R.id.p_star_1))
        playStars.add(findViewById(R.id.p_star_2))
        playStars.add(findViewById(R.id.p_star_3))
        playStars.add(findViewById(R.id.p_star_4))
        playStars.add(findViewById(R.id.p_star_5))

        playStars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                autoFillStarRight(playStarsValue, index, !playStarsValue[index])
                star(values = playStarsValue, views = playStars)
                Toast.makeText(
                    this,
                    "Play rate: ${playStarsValue.filter { it }.size.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initDanceStarsView() {
        danceStars.add(findViewById(R.id.d_star_1))
        danceStars.add(findViewById(R.id.d_star_2))
        danceStars.add(findViewById(R.id.d_star_3))
        danceStars.add(findViewById(R.id.d_star_4))
        danceStars.add(findViewById(R.id.d_star_5))

        danceStars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                autoFillStarRight(list = danceStarsValue, index, !danceStarsValue[index])
                star(values = danceStarsValue, views = danceStars)
                Toast.makeText(
                    this,
                    "Dance rate: ${danceStarsValue.filter { it }.size.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun autoFillStarRight(list: MutableList<Boolean>, index: Int, value: Boolean) {
        for (i in 0..index) {
            list[i] = value
        }
        Log.i("st", list.toString())
    }

    private fun autoFillStarLeft(index: Int, value: Boolean) {
        for (i in 0..index) {
            musicStarsValue[i] = value
        }
        Log.i("st", musicStarsValue.toString())
    }

    private fun star(views: MutableList<ImageView>, values: MutableList<Boolean>) {
        values.forEachIndexed { index, b ->
            if (b) {
                views[index].setImageResource(R.drawable.baseline_star_rate_24)
            } else {
                views[index].setImageResource(R.drawable.baseline_star_outline_24)
            }
        }
    }


    fun onClearClicked(v: View) {
        danceCheckBox.isChecked = false
        fashionCheckBox.isChecked = false
        foodCheckBox.isChecked = false
        musicCheckBox.isChecked = false
        playCheckBox.isChecked = false
        clearStars()
    }

    private fun clearStars() {
        musicStarsValue.forEach { !it }
        danceStarsValue.forEach { !it }
        foodStarsValue.forEach { !it }
        playStarsValue.forEach { !it }
        fashionStarsValue.forEach { !it }
        playStars.forEach { it.setImageResource(R.drawable.baseline_star_outline_24) }
        danceStars.forEach { it.setImageResource(R.drawable.baseline_star_outline_24) }
        musicStars.forEach { it.setImageResource(R.drawable.baseline_star_outline_24) }
        foodStars.forEach { it.setImageResource(R.drawable.baseline_star_outline_24) }
        fashionStars.forEach { it.setImageResource(R.drawable.baseline_star_outline_24) }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("save", musicStarsValue.toString())
        outState.putBooleanArray(MUSIC_RATE, musicStarsValue.toBooleanArray())

        super.onSaveInstanceState(outState)
    }
}