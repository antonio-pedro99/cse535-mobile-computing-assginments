package com.antonio20028.alarmapp.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.antonio20028.alarmapp.models.Alarm
import com.antonio20028.alarmapp.utils.AlarmServiceUtils
import com.antonio20028.alarmapp.utils.LoggingUtils
import java.util.*

class AlarmService: Service() {

    lateinit var timeHandler: Handler
    lateinit var timeRunnable: Runnable
    lateinit var timer:Timer

    override fun onCreate() {
        super.onCreate()
        timeHandler = Handler()

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
               AlarmServiceUtils.trackCurrentTime(applicationContext, alarms)
            }
        }, 0, 10000)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        LoggingUtils().showServiceStopped(applicationContext)
        AlarmServiceUtils.serviceIsRunning = !AlarmServiceUtils.serviceIsRunning
        AlarmServiceUtils.ringtone.stop()
    }
}