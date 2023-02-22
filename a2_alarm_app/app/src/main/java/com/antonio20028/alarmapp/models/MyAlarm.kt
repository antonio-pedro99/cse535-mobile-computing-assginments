package com.antonio20028.alarmapp.models

data class Alarm(
    var name: String,
    var selectedHour:Int = 0,
    var selectedMinute:Int = 0,
    var inputTime:String, //e.g 00:00
    var format: String, //AM, PM
) {

}