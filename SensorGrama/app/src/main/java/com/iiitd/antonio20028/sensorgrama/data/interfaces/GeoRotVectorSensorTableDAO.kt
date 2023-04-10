package com.iiitd.antonio20028.sensorgrama.data.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.iiitd.antonio20028.sensorgrama.data.models.GeoRotationVectorSensorModel

@Dao
interface GeoRotVectorSensorTableDAO {

    @Query("SELECT * FROM geo_rotation_vector_values")
    fun getAll():List<GeoRotationVectorSensorModel>
    @Insert
    fun insertValue(sensorValue:GeoRotationVectorSensorModel)
}