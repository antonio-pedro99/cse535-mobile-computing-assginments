package com.antonio20028.alarmapp.utils

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.util.Log
import com.antonio20028.alarmapp.models.Alarm
import java.text.SimpleDateFormat
import java.util.*

object AlarmServiceUtils {
    val INTERVAL: Long = 10000
    private val TIME_PATTERN: String = "HH:mm"

    var serviceIsRunning:Boolean = false
    lateinit var ringtone:Ringtone
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
                    ringtone =  playAlarmRingtone(context, alarm).also { ringtone ->  ringtone.play()}
                }

            }
        }

    }
    private fun playAlarmRingtone(context: Context, alarm: Alarm):Ringtone{
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val tone = RingtoneManager.getRingtone(context, notification)

        return tone
    }

}