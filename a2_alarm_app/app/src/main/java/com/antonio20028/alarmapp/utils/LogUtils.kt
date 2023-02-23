package com.antonio20028.alarmapp.utils

import android.content.Context
import android.media.Ringtone
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.Duration

class LoggingUtils {
    private val ALARM_TAG = "AlarmService"
    val ALARM_BROADCAST = "AlarmBroadcast"

    fun  showServiceStopped(context: Context){
        showLog("Service Stopped")
        showToast(context, "Service Stopped")
    }

    fun showCantStartService(context: Context){
        showToast(context, "Can't Start Service")
    }

    fun showCantStopService(context: Context){
        showToast(context, "Can't Start Service")
    }

    fun  showServiceStarted(context: Context){
        showToast(context, "Service Started")
        showLog("Service Started")
    }

    private fun showLog(msg:String){
        Log.d(ALARM_TAG, msg)
    }

    fun showToast(context: Context, msg:String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showAlarm(context: Context, message: String, duration: Int) {
        Toast.makeText(context, message, duration).show()
    }

}