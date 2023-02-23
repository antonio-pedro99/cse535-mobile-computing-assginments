package com.antonio20028.alarmapp.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.antonio20028.alarmapp.models.Alarm
import com.antonio20028.alarmapp.utils.AlarmServiceUtils
import com.antonio20028.alarmapp.utils.LoggingUtils
import com.antonio20028.alarmapp.utils.RingtonePlayer
import java.util.*

class AlarmService: Service() {
    lateinit var timer:Timer

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return  null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LoggingUtils().showServiceStarted(applicationContext)

        val alarms = intent?.getParcelableArrayListExtra<Alarm>("ALARMS")
        timer = Timer()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                AlarmServiceUtils.serviceIsRunning = true
               AlarmServiceUtils.trackCurrentTime(applicationContext, alarms)
            }
        }, 0, AlarmServiceUtils.INTERVAL)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        LoggingUtils().showServiceStopped(applicationContext)
        AlarmServiceUtils.serviceIsRunning = false
        RingtonePlayer.stopRingtone()
    }
}