package com.iiitd.antonio20028.sensorgrama.data.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.iiitd.antonio20028.sensorgrama.data.models.LightSensorModel

@Dao
interface LightSensorTableDAO {
    @Query("SELECT * FROM light_sensor_values")
    fun getAll():List<LightSensorModel>

    @Insert
    fun insertValue(sensorValue:LightSensorModel)

}