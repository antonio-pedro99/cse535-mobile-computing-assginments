package com.antonio20028.alarmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.antonio20028.alarmapp.ui.AlarmFragment
import com.antonio20028.alarmapp.viewmodels.AlarmViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragManager: FragmentManager = supportFragmentManager
        val fragTransaction: FragmentTransaction = fragManager.beginTransaction()

        val alarmFragment = AlarmFragment()
        fragTransaction.replace(R.id.container_view, alarmFragment).commit()

    }


}