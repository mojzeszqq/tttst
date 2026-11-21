package com.xb88.rider.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "bike_profile")
data class BikeProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val model: String,
    val platformCode: String,
    val engine: String,
    val power: String,
    val gearbox: String,
    val carburetor: String,
    val ignition: String,
    val oilSpec: String,
    val tankCapacityL: Double,
    val electricalSystem: String,
    val batterySpec: String,
    val mainFuse: String,
    val frontTire: String,
    val rearTire: String,
    val frontPressureBar: Double,
    val rearPressureBar: Double,
    val chainSpec: String,
    val dimensions: String,
    val valveClearanceNote: String,
    val currentMth: Double,
    val currentKm: Double,
    val notes: String,
)
