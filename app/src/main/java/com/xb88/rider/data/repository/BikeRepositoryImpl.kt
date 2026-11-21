package com.xb88.rider.data.repository

import com.xb88.rider.data.local.dao.BikeProfileDao
import com.xb88.rider.data.local.toDomain
import com.xb88.rider.data.local.toEntity
import com.xb88.rider.domain.model.BikeProfile
import com.xb88.rider.domain.repository.BikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BikeRepositoryImpl @Inject constructor(
    private val dao: BikeProfileDao,
) : BikeRepository {
    override fun observeBike(): Flow<BikeProfile?> = dao.observeFirst().map { it?.toDomain() }

    override suspend fun updateBike(profile: BikeProfile) = dao.update(profile.toEntity())

    override suspend fun updateCounters(bikeId: Long, mth: Double, km: Double) =
        dao.updateCounters(bikeId, mth, km)
}
