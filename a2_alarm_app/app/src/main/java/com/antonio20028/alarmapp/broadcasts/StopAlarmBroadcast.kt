package com.antonio20028.alarmapp.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast
import com.antonio20028.alarmapp.services.AlarmService
import com.antonio20028.alarmapp.utils.AlarmServiceUtils
import com.antonio20028.alarmapp.utils.LoggingUtils

open class StopAlarmBroadcast:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
          var intentReason: Intent? = null

        when(intent?.action) {
            Intent.ACTION_POWER_CONNECTED ->  {
                intentReason = Intent(CustomIntentFilter.ACTION_POWER_CONNECTED_DETECTED)
            }
            TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                val phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                if (phoneState == TelephonyManager.EXTRA_STATE_RINGING) {
                    intentReason = Intent(CustomIntentFilter.ACTION_PHONE_CALL_DETECTED)
                }
            }
        }
        if (context != null && intentReason != null) {
            stopService(context, intentReason)
        }
    }

    private fun stopService(context: Context, intent: Intent){
        if (AlarmServiceUtils.serviceIsRunning){
            context.sendBroadcast(intent)
        }else {
            LoggingUtils().showCantStopService(context)
        }
    }
}
