package com.antonio20028.alarmapp.data

import com.antonio20028.alarmapp.models.Alarm

class AlarmsList() {

    fun get(size:Int):MutableList<Alarm>{
        val alarmsList = mutableListOf<Alarm>()
        for (i in 0..size){
            alarmsList.add(Alarm("Antonio", "00:00", "AM"))
        }
        return alarmsList
    }
}