package com.antonio20028.alarmapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.antonio20028.alarmapp.R
import com.antonio20028.alarmapp.adapters.AlarmItemAdapter
import com.antonio20028.alarmapp.data.AlarmsList
import com.antonio20028.alarmapp.models.Alarm
import com.antonio20028.alarmapp.services.AlarmService
import com.antonio20028.alarmapp.utils.AlarmServiceUtils

class AlarmFragment: Fragment() , OnItemClickListener{

    private val alarmsCapacity = listOf<String>("1", "2", "3", "4", "5")
    lateinit var alarmQuantityTextView:  AutoCompleteTextView
    lateinit var alarmRecyclerView: RecyclerView
    lateinit var btnStartAlarmService:Button
    lateinit var btnStopAlarmService:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alarm_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmQuantityTextView = view.findViewById(R.id.alarms_quantity)
        alarmRecyclerView = view.findViewById(R.id.alarms_recyclerview)
        btnStartAlarmService = view.findViewById(R.id.btn_start_alarm)
        btnStopAlarmService = view.findViewById(R.id.btn_stop_alarm)

        buildAlarmQuantityDropDownItems()

        alarmRecyclerView.setHasFixedSize(true)
        alarmRecyclerView.layoutManager = LinearLayoutManager(context)

        alarmQuantityTextView.onItemClickListener = this
        btnStopAlarmService.setOnClickListener(View.OnClickListener { stopAlarmService() })
        btnStartAlarmService.setOnClickListener(View.OnClickListener { startAlarmService()})

    }

    private fun startAlarmService(){
        val intent = Intent(requireContext(), AlarmService::class.java)
        requireContext().startService(intent)
    }
   private fun stopAlarmService(){
        val intent = Intent(requireContext(), AlarmService::class.java)
        requireContext().stopService(intent)
    }

    private fun buildAlarmQuantityDropDownItems() {
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, alarmsCapacity)
        alarmQuantityTextView.setAdapter(adapter)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val alarms = AlarmsList().get(position)
        alarmRecyclerView.adapter = AlarmItemAdapter(requireContext(), alarms)
    }
}