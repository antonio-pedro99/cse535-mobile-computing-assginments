package com.antonio20028.alarmapp.ui

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

class AlarmFragment: Fragment() , OnItemClickListener{

    private val alarmsCapacity = listOf<String>("1", "2", "3", "4", "5")
    lateinit var alarmQuantityTextView:  AutoCompleteTextView

    lateinit var alarmRecyclerView: RecyclerView

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

        buildAlarmQuantityDropDownItems()

        alarmRecyclerView.setHasFixedSize(true)
        alarmRecyclerView.layoutManager = LinearLayoutManager(context)



        alarmQuantityTextView.onItemClickListener = this

    }

    private fun buildAlarmQuantityDropDownItems() {
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, alarmsCapacity)
        alarmQuantityTextView.setAdapter(adapter)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      /*  alarms = AlarmsList().get(position)

        alarmRecyclerView.adapter = AlarmItemAdapter(requireContext(), alarms)

        Log.d("T", "Item selected ${alarms[0].inputTime}")*/
        val alarms = AlarmsList().get(position)
        alarmRecyclerView.adapter = AlarmItemAdapter(alarms)
    }
}