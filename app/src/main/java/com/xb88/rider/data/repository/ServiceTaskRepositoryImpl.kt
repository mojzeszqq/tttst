package com.xb88.rider.data.repository

import com.xb88.rider.data.local.dao.ServiceTaskDao
import com.xb88.rider.data.local.toDomain
import com.xb88.rider.domain.model.ServiceTask
import com.xb88.rider.domain.repository.ServiceTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ServiceTaskRepositoryImpl @Inject constructor(
    private val dao: ServiceTaskDao,
) : ServiceTaskRepository {
    override fun observeTasks(): Flow<List<ServiceTask>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }
}
