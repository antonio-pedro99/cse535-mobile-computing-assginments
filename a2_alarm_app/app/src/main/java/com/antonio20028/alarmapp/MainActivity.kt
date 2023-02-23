package com.antonio20028.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.antonio20028.alarmapp.broadcasts.StopAlarmBroadcast
import com.antonio20028.alarmapp.ui.AlarmFragment

class MainActivity : AppCompatActivity() {


    lateinit var stopAlarmBroadcast:StopAlarmBroadcast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragManager: FragmentManager = supportFragmentManager
        val fragTransaction: FragmentTransaction = fragManager.beginTransaction()

        fragTransaction.replace(R.id.container_view, AlarmFragment()).commit()

        setupAlarmBroadcastReceiver()


    }


    override fun onStart() {
        super.onStart()

    }
    private fun setupAlarmBroadcastReceiver(){
        val  intFilter = IntentFilter()
        intFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        intFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        stopAlarmBroadcast =  StopAlarmBroadcast()
        registerReceiver(stopAlarmBroadcast, intFilter)
    }

    override fun onDestroy() {
        unregisterReceiver(stopAlarmBroadcast)
        super.onDestroy()
    }
}