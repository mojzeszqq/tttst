package com.xb88.rider.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "service_task")
data class ServiceTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    /** Name of [com.xb88.rider.domain.model.ServiceCategory]. */
    val category: String,
    val intervalMth: Double,
    val intervalDays: Int?,
    val lastDoneMth: Double,
    val lastDoneEpochMillis: Long?,
    val notes: String,
    val isActive: Boolean,
)
