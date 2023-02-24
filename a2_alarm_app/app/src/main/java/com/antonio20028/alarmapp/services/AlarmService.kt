package com.antonio20028.alarmapp.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.antonio20028.alarmapp.broadcasts.CustomIntentFilter
import com.antonio20028.alarmapp.models.Alarm
import com.antonio20028.alarmapp.utils.AlarmServiceUtils
import com.antonio20028.alarmapp.utils.LoggingUtils
import com.antonio20028.alarmapp.utils.RingtonePlayer
import java.util.*

class AlarmService: Service() {
    lateinit var timer:Timer

    lateinit var stopReasonBroadcastReceiver : BroadcastReceiver
    override fun onCreate() {
        super.onCreate()
        setupAlarmServiceStopBroadcastReceiver()
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

        AlarmServiceUtils.serviceIsRunning = false
        if (RingtonePlayer.isPlaying() == true){
            RingtonePlayer.stopRingtone()
        }
        applicationContext.unregisterReceiver(stopReasonBroadcastReceiver)
    }
    private fun setupAlarmServiceStopBroadcastReceiver() {
        val intFilter = IntentFilter(CustomIntentFilter.ACTION_USER_STOP_DETECTED)
        intFilter.addAction(CustomIntentFilter.ACTION_PHONE_CALL_DETECTED)
        intFilter.addAction(CustomIntentFilter.ACTION_POWER_CONNECTED_DETECTED)

        stopReasonBroadcastReceiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var message = "Unknown"

                when(intent?.action){
                    CustomIntentFilter.ACTION_USER_STOP_DETECTED -> message = "by user"
                    CustomIntentFilter.ACTION_PHONE_CALL_DETECTED -> message = "by a call"
                    CustomIntentFilter.ACTION_POWER_CONNECTED_DETECTED -> message = "by power connection"
                }
                LoggingUtils().showServiceStopped(applicationContext, message)
                stopSelf()
            }
        }
        applicationContext.registerReceiver(stopReasonBroadcastReceiver, intFilter)
    }
}