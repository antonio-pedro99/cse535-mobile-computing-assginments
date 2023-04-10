package com.iiitd.antonio20028.sensorgrama.data.interfaces
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.iiitd.antonio20028.sensorgrama.data.models.ProximitySensorModel

@Dao
interface ProximitySensorTableDAO {

    @Query("SELECT * FROM proximity_sensor_values")
    fun getAll():List<ProximitySensorModel>

    @Insert
    fun insertValue(sensorValue:ProximitySensorModel)
}