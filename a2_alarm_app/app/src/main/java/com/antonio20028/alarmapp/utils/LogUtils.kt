package com.antonio20028.alarmapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

class LoggingUtils {
    private val ALARM_TAG = "AlarmService"
    private val ALARM_BROADCAST = "AlarmBroadcast"

    fun  showServiceStopped(context: Context){
        showLog("Service Stopped")
        showToast(context, "Service Stopped")
    }

    fun  showServiceStarted(context: Context){
        showToast(context, "Service Started")
        showLog("Service Started")
    }

    private fun showLog(msg:String){
        Log.d(ALARM_TAG, msg)
    }

    private fun showToast(context: Context, msg:String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}