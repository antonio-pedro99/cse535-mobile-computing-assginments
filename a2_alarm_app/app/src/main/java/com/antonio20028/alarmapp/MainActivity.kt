package com.antonio20028.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.antonio20028.alarmapp.broadcasts.CustomIntentFilter
import com.antonio20028.alarmapp.broadcasts.StopAlarmBroadcast
import com.antonio20028.alarmapp.ui.AlarmFragment

class MainActivity : AppCompatActivity() {


    lateinit var stopAlarmBroadcast:StopAlarmBroadcast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askBatteryUserPermission()
        val fragManager: FragmentManager = supportFragmentManager
        val fragTransaction: FragmentTransaction = fragManager.beginTransaction()

        fragTransaction.replace(R.id.container_view, AlarmFragment()).commit()
        setupAlarmBroadcastReceiver()
    }

    private fun setupAlarmBroadcastReceiver(){
        val  intFilter = IntentFilter()
        stopAlarmBroadcast = StopAlarmBroadcast()
        intFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        intFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intFilter.addAction(Intent.ACTION_BATTERY_LOW)
       registerReceiver( stopAlarmBroadcast,intFilter)
    }


    private fun askBatteryUserPermission() {
        if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            Log.d("PERMISSION", "Not Granted")
            requestPermissions( arrayOf(android.Manifest.permission.READ_PHONE_STATE), 1)
        } else {
            Log.d("PERMISSION", "Granted")
        }
    }

    override fun onDestroy() {
        unregisterReceiver(stopAlarmBroadcast)
        super.onDestroy()
    }
}