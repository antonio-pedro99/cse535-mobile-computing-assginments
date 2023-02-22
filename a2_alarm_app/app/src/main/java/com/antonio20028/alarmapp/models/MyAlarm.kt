package com.antonio20028.alarmapp.models

import android.os.Parcel
import android.os.Parcelable

data class Alarm(
    var name: String?,
    var selectedHour:Int = 0,
    var selectedMinute:Int = 0,
    var inputTime: String?, //e.g 00:00
    var format: String?, //AM, PM
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(selectedHour)
        parcel.writeInt(selectedMinute)
        parcel.writeString(inputTime)
        parcel.writeString(format)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alarm> {
        override fun createFromParcel(parcel: Parcel): Alarm {
            return Alarm(parcel)
        }

        override fun newArray(size: Int): Array<Alarm?> {
            return arrayOfNulls(size)
        }
    }

}