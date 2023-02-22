package com.antonio20028.alarmapp.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.antonio20028.alarmapp.utils.AlarmServiceUtils
import com.antonio20028.alarmapp.utils.LoggingUtils

class AlarmService: Service() {


    lateinit var timeHandler: Handler
    lateinit var timeRunnable: Runnable

    override fun onCreate() {
        super.onCreate()
        timeHandler = Handler()
        timeRunnable = Runnable {
            AlarmServiceUtils().trackCurrentTime(applicationContext)
            timeHandler.postDelayed(timeRunnable, AlarmServiceUtils().INTERVAL)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return  null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LoggingUtils().showServiceStarted(applicationContext)
        timeHandler.postDelayed(timeRunnable, AlarmServiceUtils().INTERVAL)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        timeHandler.removeCallbacks(timeRunnable)
        LoggingUtils().showServiceStopped(applicationContext)
    }
}