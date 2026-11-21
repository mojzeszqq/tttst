package com.xb88.rider.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xb88.rider.data.local.entity.ServiceTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceTaskDao {
    @Query("SELECT * FROM service_task ORDER BY intervalMth, id")
    fun observeAll(): Flow<List<ServiceTaskEntity>>

    @Query("SELECT COUNT(*) FROM service_task")
    suspend fun count(): Int

    @Insert
    suspend fun insertAll(entities: List<ServiceTaskEntity>)
}
