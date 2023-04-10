package com.iiitd.antonio20028.sensorgrama.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iiitd.antonio20028.sensorgrama.data.interfaces.GeoRotVectorSensorTableDAO
import com.iiitd.antonio20028.sensorgrama.data.interfaces.LightSensorTableDAO
import com.iiitd.antonio20028.sensorgrama.data.interfaces.ProximitySensorTableDAO
import com.iiitd.antonio20028.sensorgrama.data.models.GeoRotationVectorSensorModel
import com.iiitd.antonio20028.sensorgrama.data.models.LightSensorModel
import com.iiitd.antonio20028.sensorgrama.data.models.ProximitySensorModel

@Database(entities = [
    LightSensorModel::class,
    GeoRotationVectorSensorModel::class,
    ProximitySensorModel::class], version = 1)
abstract class SensorDatabase:RoomDatabase() {
    abstract fun lightSensorTableDao():LightSensorTableDAO
    abstract fun proximitySensorTableDao():ProximitySensorTableDAO
    abstract fun geoRotVectorTableDao():GeoRotVectorSensorTableDAO

    companion object {
        private var INSTANCE : SensorDatabase? = null
                fun getDatabaseInstance(context:Context):SensorDatabase {
                    return  INSTANCE ?: synchronized(this){
                        val instance = Room.databaseBuilder(
                            context.applicationContext,
                            SensorDatabase::class.java, "sensor-grama-db").build()
                        INSTANCE = instance
                        instance
                    }
                }
    }
}