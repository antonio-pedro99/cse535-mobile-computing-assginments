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
import androidx.recyclerview.widget.RecyclerView
import com.antonio20028.alarmapp.R
import com.antonio20028.alarmapp.data.AlarmsList
import com.antonio20028.alarmapp.models.Alarm

class AlarmFragment: Fragment() , OnItemClickListener{

    private val alarmsCapacity = listOf<String>("1", "2", "3", "4", "5")
    lateinit var alarmQuantityTextView:  AutoCompleteTextView
    lateinit var alarms:MutableList<Alarm>
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
        alarmQuantityTextView.onItemClickListener = this

    }

    private fun buildAlarmQuantityDropDownItems() {
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, alarmsCapacity)
        alarmQuantityTextView.setAdapter(adapter)
    }

    private fun buildRecyclerViewItems(){
        //set up the list of alarms to the recycler view
    }

    private fun setupAlarms() {
        //create a list of alarms
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        alarms = AlarmsList().get(position)
        Log.d("T", "Item selected ${alarms[0].inputTime}")
    }
}