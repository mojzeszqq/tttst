package com.xb88.rider.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xb88.rider.data.local.entity.BikeProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BikeProfileDao {
    @Query("SELECT * FROM bike_profile ORDER BY id LIMIT 1")
    fun observeFirst(): Flow<BikeProfileEntity?>

    @Query("SELECT COUNT(*) FROM bike_profile")
    suspend fun count(): Int

    @Insert
    suspend fun insert(entity: BikeProfileEntity): Long

    @Update
    suspend fun update(entity: BikeProfileEntity)

    @Query("UPDATE bike_profile SET currentMth = :mth, currentKm = :km WHERE id = :id")
    suspend fun updateCounters(id: Long, mth: Double, km: Double)
}
