package com.iiitd.antonio20028.sensorgrama.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_rotation_vector_values")
data class GeoRotationVectorSensorModel(
    @PrimaryKey(autoGenerate = true) val id:Int? = null,
    @ColumnInfo(name = "x_axis") val xAxis:Float,
    @ColumnInfo(name = "y_axis") val yAxis:Float,
    @ColumnInfo(name = "z_axis") val zAxis: Float
)
