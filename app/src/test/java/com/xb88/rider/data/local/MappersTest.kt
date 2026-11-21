package com.xb88.rider.data.local

import com.xb88.rider.domain.model.ServiceCategory
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `bike profile survives entity to domain round trip`() {
        val entity = SeedData.bikeProfile.copy(id = 7, currentMth = 12.5, currentKm = 340.2, notes = "x")
        assertEquals(entity, entity.toDomain().toEntity())
    }

    @Test
    fun `unknown service category falls back to ENGINE`() {
        val entity = SeedData.serviceTasks.first().copy(category = "NOT_A_CATEGORY")
        assertEquals(ServiceCategory.ENGINE, entity.toDomain().category)
    }
}
