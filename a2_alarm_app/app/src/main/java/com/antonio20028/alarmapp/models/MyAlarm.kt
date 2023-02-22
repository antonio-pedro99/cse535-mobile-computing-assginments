package com.antonio20028.alarmapp.models

data class Alarm(
    val name: String,
    val inputTime:String, //e.g 00:00
    val format: String, //AM, PM
) {

}