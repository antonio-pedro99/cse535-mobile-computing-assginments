package com.iiitd.antonio20028.sensorgrama.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "proximity_sensor_values")
data class ProximitySensorModel(
    @PrimaryKey(autoGenerate = true) val id:Int? = null,
    @ColumnInfo(name ="distance") val distance:Float?
)
