package com.antonio20028.alarmapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antonio20028.alarmapp.R
import com.antonio20028.alarmapp.adapters.AlarmItemAdapter
import com.antonio20028.alarmapp.broadcasts.CustomIntentFilter
import com.antonio20028.alarmapp.data.AlarmsList
import com.antonio20028.alarmapp.models.Alarm
import com.antonio20028.alarmapp.services.AlarmService
import com.antonio20028.alarmapp.utils.AlarmServiceUtils
import com.antonio20028.alarmapp.utils.LoggingUtils

class AlarmFragment: Fragment() , OnItemClickListener{

    private val alarmsCapacity = listOf<String>("1", "2", "3", "4", "5")
    lateinit var alarmQuantityTextView:  AutoCompleteTextView
    lateinit var alarmRecyclerView: RecyclerView
    lateinit var btnStartAlarmService:Button
    lateinit var btnStopAlarmService:Button
    private var alarms = arrayListOf<Alarm>()
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
        btnStopAlarmService.setOnClickListener { stopAlarmService() }
        btnStartAlarmService.setOnClickListener { startAlarmService() }

    }

    private fun startAlarmService(){
        val intent = Intent(requireContext(), AlarmService::class.java)
        if (alarms.isNotEmpty()) {
            intent.putExtra("ALARMS", alarms)
            requireContext().startService(intent)
        } else {
            LoggingUtils().showCantStartService(requireContext())
        }
    }
   private fun stopAlarmService(){
        if (AlarmServiceUtils.serviceIsRunning){
            val intent = Intent(CustomIntentFilter.ACTION_USER_STOP_DETECTED)
            requireContext().sendBroadcast(intent)
        }else {
            LoggingUtils().showCantStopService(requireContext())
        }
    }

    private fun buildAlarmQuantityDropDownItems() {
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, alarmsCapacity)
        alarmQuantityTextView.setAdapter(adapter)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        alarms = AlarmsList().get(position) as ArrayList<Alarm>
        alarmRecyclerView.adapter = AlarmItemAdapter(requireContext(), alarms)
    }
}