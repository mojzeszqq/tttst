package com.xb88.rider.data.local

import com.xb88.rider.data.local.entity.BikeProfileEntity
import com.xb88.rider.data.local.entity.ServiceTaskEntity
import com.xb88.rider.domain.model.BikeProfile
import com.xb88.rider.domain.model.ServiceCategory
import com.xb88.rider.domain.model.ServiceTask

fun BikeProfileEntity.toDomain() = BikeProfile(
    id = id,
    model = model,
    platformCode = platformCode,
    engine = engine,
    power = power,
    gearbox = gearbox,
    carburetor = carburetor,
    ignition = ignition,
    oilSpec = oilSpec,
    tankCapacityL = tankCapacityL,
    electricalSystem = electricalSystem,
    batterySpec = batterySpec,
    mainFuse = mainFuse,
    frontTire = frontTire,
    rearTire = rearTire,
    frontPressureBar = frontPressureBar,
    rearPressureBar = rearPressureBar,
    chainSpec = chainSpec,
    dimensions = dimensions,
    valveClearanceNote = valveClearanceNote,
    currentMth = currentMth,
    currentKm = currentKm,
    notes = notes,
)

fun BikeProfile.toEntity() = BikeProfileEntity(
    id = id,
    model = model,
    platformCode = platformCode,
    engine = engine,
    power = power,
    gearbox = gearbox,
    carburetor = carburetor,
    ignition = ignition,
    oilSpec = oilSpec,
    tankCapacityL = tankCapacityL,
    electricalSystem = electricalSystem,
    batterySpec = batterySpec,
    mainFuse = mainFuse,
    frontTire = frontTire,
    rearTire = rearTire,
    frontPressureBar = frontPressureBar,
    rearPressureBar = rearPressureBar,
    chainSpec = chainSpec,
    dimensions = dimensions,
    valveClearanceNote = valveClearanceNote,
    currentMth = currentMth,
    currentKm = currentKm,
    notes = notes,
)

fun ServiceTaskEntity.toDomain() = ServiceTask(
    id = id,
    title = title,
    category = ServiceCategory.entries.firstOrNull { it.name == category } ?: ServiceCategory.ENGINE,
    intervalMth = intervalMth,
    intervalDays = intervalDays,
    lastDoneMth = lastDoneMth,
    lastDoneEpochMillis = lastDoneEpochMillis,
    notes = notes,
    isActive = isActive,
)
