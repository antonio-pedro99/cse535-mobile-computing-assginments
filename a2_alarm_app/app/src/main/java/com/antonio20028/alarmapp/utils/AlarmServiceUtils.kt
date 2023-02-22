package com.antonio20028.alarmapp.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import com.antonio20028.alarmapp.services.AlarmService
import java.text.SimpleDateFormat
import java.util.*

class AlarmServiceUtils {
    val INTERVAL: Long = 10000
    private val TIME_PATTERN: String = "HH:mm"

    var serviceIsRunning:Boolean = false

    fun trackCurrentTime(context:Context) {
        val cal = Calendar.getInstance()
        val currentTime = SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(cal.time)
        Log.d("TIME", "Time: $currentTime")
    }


}