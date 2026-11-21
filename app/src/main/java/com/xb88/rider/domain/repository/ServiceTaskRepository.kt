package com.xb88.rider.domain.repository

import com.xb88.rider.domain.model.ServiceTask
import kotlinx.coroutines.flow.Flow

interface ServiceTaskRepository {
    fun observeTasks(): Flow<List<ServiceTask>>
}
