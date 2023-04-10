package com.iiitd.antonio20028.sensorgrama.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "light_sensor_values")
data class LightSensorModel(
    @PrimaryKey(autoGenerate = true) val id:Int? = null,
    @ColumnInfo(name = "illumination") val illumination:Float
)
