package com.xb88.rider.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xb88.rider.data.local.dao.BikeProfileDao
import com.xb88.rider.data.local.dao.ServiceTaskDao
import com.xb88.rider.data.local.entity.BikeProfileEntity
import com.xb88.rider.data.local.entity.ServiceTaskEntity

@Database(
    entities = [BikeProfileEntity::class, ServiceTaskEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class XB88Database : RoomDatabase() {
    abstract fun bikeProfileDao(): BikeProfileDao
    abstract fun serviceTaskDao(): ServiceTaskDao

    companion object {
        const val NAME = "xb88_rider.db"
    }
}
