package com.antonio20028.alarmapp

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.antonio20028.alarmapp.broadcasts.StopAlarmBroadcast
import com.antonio20028.alarmapp.ui.AlarmFragment
import com.antonio20028.alarmapp.viewmodels.AlarmViewModel

class MainActivity : AppCompatActivity() {


    lateinit var stopAlarmBroadcast:StopAlarmBroadcast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragManager: FragmentManager = supportFragmentManager
        val fragTransaction: FragmentTransaction = fragManager.beginTransaction()

        val alarmFragment = AlarmFragment()
        fragTransaction.replace(R.id.container_view, alarmFragment).commit()

        stopAlarmBroadcast = StopAlarmBroadcast()
        setupAlarmBroadcastReceiver()
    }

    private fun setupAlarmBroadcastReceiver(){
        val  intFilter = IntentFilter()
        intFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        intFilter.addAction(Intent.ACTION_POWER_CONNECTED);

        registerReceiver(stopAlarmBroadcast, intFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(stopAlarmBroadcast)
    }
}