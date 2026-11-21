package com.xb88.rider.domain.model

enum class ServiceCategory { ENGINE, AIR_INTAKE, DRIVETRAIN, CHASSIS, IGNITION, SUSPENSION }

data class ServiceTask(
    val id: Long = 0,
    val title: String,
    val category: ServiceCategory,
    val intervalMth: Double,
    val intervalDays: Int?,
    val lastDoneMth: Double,
    val lastDoneEpochMillis: Long?,
    val notes: String,
    val isActive: Boolean,
)
