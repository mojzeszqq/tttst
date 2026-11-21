package com.xb88.rider.domain.repository

import com.xb88.rider.domain.model.BikeProfile
import kotlinx.coroutines.flow.Flow

interface BikeRepository {
    /** Emits the active bike profile, or null until the first-launch seed has been written. */
    fun observeBike(): Flow<BikeProfile?>
    suspend fun updateBike(profile: BikeProfile)
    suspend fun updateCounters(bikeId: Long, mth: Double, km: Double)
}
