package com.antonio20028.alarmapp.utils

import android.content.Context
import android.util.Log
import com.antonio20028.alarmapp.models.Alarm
import java.text.SimpleDateFormat
import java.util.*

class AlarmServiceUtils {
    val INTERVAL: Long = 10000
    private val TIME_PATTERN: String = "HH:mm"

    var serviceIsRunning:Boolean = false

    fun trackCurrentTime(context:Context, alarms: ArrayList<Alarm>?) {
        val cal = Calendar.getInstance()
        val currentTime = SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(cal.time)
        val hour = currentTime.split(":").first().toInt()
        val minute = currentTime.split(":").last().toInt()
        val period = if (cal.get(Calendar.AM_PM) == Calendar.PM) "PM" else "AM"
        if (alarms != null) {
            for (alarm in alarms){
                if (hour == alarm.selectedHour && minute == alarm.selectedMinute && period == alarm.format){
                    Log.d("TIME", "${alarm.name} is ringing")
                }

            }
        }

    }


}