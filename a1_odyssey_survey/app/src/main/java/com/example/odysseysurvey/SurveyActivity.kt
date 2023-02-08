package com.example.odysseysurvey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.io.Serializable

const val MUSIC_RATE = "music"
const val DANCE_RATE = "dance"
const val FASHION_RATE = "fashion"
const val FOOD_RATE = "food"
const val PLAY_RATE = "play"
const val TAG = "SurveyActivity"

class SurveyActivity : AppCompatActivity() {

    private var danceStarsValue = mutableListOf<Boolean>(false, false, false, false, false)
    private var playStarsValue = mutableListOf<Boolean>(false, false, false, false, false)
    private var musicStarsValue = mutableListOf<Boolean>(false, false, false, false, false)
    private var foodStarsValue = mutableListOf<Boolean>(false, false, false, false, false)
    private var fashionStarsValue = mutableListOf<Boolean>(false, false, false, false, false)

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
    private var activityState = "onCreate"
    private var attendedEvent = mutableMapOf<String, Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils().showLogToast(
            this, activityName = TAG, stateFrom = "non-existing", stateTo = activityState
        )
        attendName = intent.getStringExtra("name").toString()
        attendRole = intent.getStringExtra("role").toString()
        setContentView(R.layout.activity_survey)
        setTitle(R.string.survey_page_title)

        saluteTextView = findViewById(R.id.salute)
        val formatted = resources.getString(R.string.option_event, attendName)

        saluteTextView.text = formatted
        initializeViews()
        setStarsVisibility()
        if (savedInstanceState != null) {
            musicStarsValue = savedInstanceState.getBooleanArray(MUSIC_RATE)?.toMutableList()!!
            danceStarsValue = savedInstanceState.getBooleanArray(DANCE_RATE)?.toMutableList()!!
            fashionStarsValue = savedInstanceState.getBooleanArray(FASHION_RATE)?.toMutableList()!!
            foodStarsValue = savedInstanceState.getBooleanArray(FOOD_RATE)?.toMutableList()!!
            playStarsValue = savedInstanceState.getBooleanArray(PLAY_RATE)?.toMutableList()!!
            star(musicStars, musicStarsValue)
            star(foodStars, foodStarsValue)
            star(fashionStars, fashionStarsValue)
            star(playStars, playStarsValue)
            star(danceStars, danceStarsValue)
        }


    }

    fun goToNextPage(view: View) {
        val confirmationIntent = Intent(this, ConfirmationActivity::class.java)
        confirmationIntent.putExtra("SurveyEventAttendeeName", attendName)
        confirmationIntent.putExtra("SurveyEventAttendeeRole", attendRole)
        var valid = false
        if (musicCheckBox.isChecked) {
            valid = true
            attendedEvent["Music"] = musicStarsValue.filter { it }.size
        }
        if (danceCheckBox.isChecked) {
            valid = true
            attendedEvent["Dance"] = danceStarsValue.filter { it }.size
        }
        if (playCheckBox.isChecked) {
            valid = true
            attendedEvent["Play"] = playStarsValue.filter { it }.size
        }
        if (foodCheckBox.isChecked) {
            valid = true
            attendedEvent["Food"] = foodStarsValue.filter { it }.size
        }
        if (fashionCheckBox.isChecked) {
            valid = true
            attendedEvent["Fashion"] = fashionStarsValue.filter { it }.size
        }

        if (valid) {
            confirmationIntent.putExtra("events", attendedEvent as Serializable)
            startActivity(confirmationIntent)
        } else {
            Toast.makeText(
                this,
                "You have to select at least one event",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRestart() {
        super.onRestart()
        Utils().showLogToast(
            this, activityName = TAG, stateFrom = activityState, stateTo = "onRestart"
        )
        activityState = "onRestart"
    }

    override fun onStart() {
        super.onStart()
        Utils().showLogToast(
            this, activityName = TAG, stateFrom = activityState, stateTo = "onStart"
        )
        activityState = "onStart"
    }

    override fun onStop() {
        super.onStop()
        Utils().showLogToast(
            this, activityName = TAG, stateFrom = activityState, stateTo = "onStop"
        )
        activityState = "onStop"
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils().showLogToast(
            this, activityName = TAG, stateFrom = activityState, stateTo = "onDestroy"
        )
        activityState = "onDestroy"
    }

    override fun onResume() {
        super.onResume()
        Utils().showLogToast(
            this, activityName = TAG, stateFrom = activityState, stateTo = "onResume"
        )
        activityState = "onResume"
    }

    override fun onPause() {
        super.onPause()
        Utils().showLogToast(
            this, activityName = TAG, stateFrom = activityState, stateTo = "onPause"
        )
        activityState = "onPause"
    }

    private fun setStarsVisibility() {
        musicCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                musicStarsView.visibility = View.VISIBLE
            } else {
                musicStarsView.visibility = View.INVISIBLE
            }
        }
        fashionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                fashionStarsView.visibility = View.VISIBLE
            } else {
                fashionStarsView.visibility = View.INVISIBLE
            }
        }

        playCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                playStarsView.visibility = View.VISIBLE
            } else {
                playStarsView.visibility = View.INVISIBLE
            }
        }

        danceCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                danceStarsView.visibility = View.VISIBLE
            } else {
                danceStarsView.visibility = View.INVISIBLE
            }
        }

        foodCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                foodStarsView.visibility = View.VISIBLE
            } else {
                foodStarsView.visibility = View.INVISIBLE
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
            }
        }
    }

    private fun autoFillStarRight(list: MutableList<Boolean>, index: Int, value: Boolean) {
        for (i in 0..index) {
            list[i] = value
        }

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
        outState.putBooleanArray(MUSIC_RATE, musicStarsValue.toBooleanArray())
        outState.putBooleanArray(DANCE_RATE, danceStarsValue.toBooleanArray())
        outState.putBooleanArray(FASHION_RATE, fashionStarsValue.toBooleanArray())
        outState.putBooleanArray(FOOD_RATE, foodStarsValue.toBooleanArray())
        outState.putBooleanArray(PLAY_RATE, playStarsValue.toBooleanArray())

        super.onSaveInstanceState(outState)
    }

}