package com.antonio20028.alarmapp.data

import com.antonio20028.alarmapp.models.Alarm

class AlarmsList() {

    fun get(size:Int):MutableList<Alarm>{
        val alarmsList = mutableListOf<Alarm>()
        for (i in 0..size){
            alarmsList.add(Alarm("Alarm ${i+1}", 0, 0,"00:00 AM", "AM"))
        }
        return alarmsList
    }
}