package com.antonio20028.alarmapp.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.antonio20028.alarmapp.services.AlarmService
import com.antonio20028.alarmapp.utils.AlarmServiceUtils
import com.antonio20028.alarmapp.utils.LoggingUtils
import com.antonio20028.alarmapp.utils.RingtonePlayer

open class StopAlarmBroadcast:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(LoggingUtils().ALARM_BROADCAST, "${intent?.action}")
        when (intent?.action) {
            Intent.ACTION_BATTERY_LOW ->  Toast.makeText(context, "Battery Low", Toast.LENGTH_LONG).show()
            Intent.ACTION_POWER_CONNECTED ->  {
                Toast.makeText(context,"Power Connected", Toast.LENGTH_LONG).show()
                val serviceIntent = Intent(context, AlarmService::class.java)
                if (AlarmServiceUtils.serviceIsRunning){
                    context?.stopService(serviceIntent)
                }else {
                    if (context != null) {
                        LoggingUtils().showCantStopService(context)
                    }
                }

            }
            Intent.ACTION_BATTERY_OKAY ->  Toast.makeText(context, "Battery Okay", Toast.LENGTH_LONG).show()
            TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                val phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                if (phoneState == TelephonyManager.EXTRA_STATE_RINGING) {
                    Toast.makeText(context, "Incoming call", Toast.LENGTH_LONG).show()
                }
            }
            else ->  Toast.makeText(context, intent?.action, Toast.LENGTH_LONG).show()
        }

    }
}