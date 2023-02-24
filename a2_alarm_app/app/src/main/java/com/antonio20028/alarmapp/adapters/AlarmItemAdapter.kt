package com.antonio20028.alarmapp.adapters

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonio20028.alarmapp.R
import com.antonio20028.alarmapp.models.Alarm
import com.antonio20028.alarmapp.utils.TimePickerUtils
import org.w3c.dom.Text

class AlarmItemAdapter(private val context: Context, private val alarms:List<Alarm>):
    RecyclerView.Adapter<AlarmItemAdapter.AlarmItemViewHolder>() {

    class AlarmItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val alarmNameTextVew: TextView = view.findViewById(R.id.name)
        val alarmInputTimeTextView: TextView = view.findViewById(R.id.alarm_input_time)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmItemViewHolder {
        val layoutAdapter = LayoutInflater.from(parent.context).inflate(R.layout.alarm_recyclerview_item, parent, false)
        return AlarmItemViewHolder(layoutAdapter)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(holder: AlarmItemViewHolder, position: Int) {
        val item : Alarm = alarms[position]
        holder.alarmNameTextVew.text = item.name
        val timing = context.resources.getString(R.string.alarm_timing, "${item.inputTime} ${item.format}")
        holder.alarmInputTimeTextView.text = timing
        holder.itemView.setOnClickListener {
           TimePickerUtils().onTimeClicked(context, item, holder)
        }
    }


}