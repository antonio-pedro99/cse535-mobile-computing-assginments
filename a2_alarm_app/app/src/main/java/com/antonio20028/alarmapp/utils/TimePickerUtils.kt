package com.antonio20028.alarmapp.utils

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import com.antonio20028.alarmapp.adapters.AlarmItemAdapter
import com.antonio20028.alarmapp.models.Alarm

class TimePickerUtils {

    fun onTimeClicked(context:Context, alarm: Alarm, holder: AlarmItemAdapter.AlarmItemViewHolder) {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->

                alarm.selectedHour = hourOfDay
                alarm.selectedMinute = minute
                alarm.format = getPeriodOfTime(hourOfDay)
                alarm.inputTime = "${alarm.selectedHour}:${alarm.selectedMinute}"

                holder.alarmInputTimeTextView.text = alarm.inputTime
            },
            alarm.selectedHour,
            alarm.selectedMinute,

            false // Set this to true if you want to use 24-hour time format
        )
        // Show the dialog
        timePickerDialog.show()
    }

    private fun getPeriodOfTime(hour:Int):String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        val format = calendar.get(Calendar.AM_PM)
        return  if (format == Calendar.AM) "AM" else "PM"
    }
}