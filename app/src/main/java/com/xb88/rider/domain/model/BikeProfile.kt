package com.xb88.rider.domain.model

/**
 * Clean domain model of the user's motorcycle. Every field is user-editable;
 * defaults come from the verified Asix XB-88 300cc (AGB40-1) factory data.
 */
data class BikeProfile(
    val id: Long = 0,
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
