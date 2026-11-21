package com.xb88.rider.data.local

import com.xb88.rider.domain.model.ServiceCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SeedDataTest {

    private val bike = SeedData.bikeProfile

    @Test
    fun `bike profile matches verified AGB40-1 factory data`() {
        assertEquals("Asix XB-88 300cc", bike.model)
        assertEquals("AGB40-1", bike.platformCode)
        assertTrue(bike.engine.contains("175FMN"))
        assertEquals("22 KM", bike.power)
        assertTrue(bike.carburetor.contains("Nibbi PE30"))
        assertEquals("CDI", bike.ignition)
        assertTrue(bike.oilSpec.contains("10W-40"))
        assertTrue(bike.oilSpec.contains("JASO MA2"))
        assertEquals(6.0, bike.tankCapacityL, 0.0)
        assertEquals("12V 5Ah", bike.batterySpec)
        assertEquals("10A", bike.mainFuse)
        assertEquals("80/100-21\"", bike.frontTire)
        assertEquals("90/100-18\"", bike.rearTire)
        assertEquals(1.3, bike.frontPressureBar, 0.0)
        assertEquals(1.3, bike.rearPressureBar, 0.0)
        assertEquals("520H", bike.chainSpec)
        assertEquals(0.0, bike.currentMth, 0.0)
    }

    @Test
    fun `default service tasks use the off-road intervals from CLAUDE md`() {
        val intervalsByKeyword = mapOf(
            "oleju" to 12.0,
            "Filtr powietrza" to 5.0,
            "520H" to 1.0,
            "bolt check" to 5.0,
            "Luz zaworowy" to 25.0,
            "Świeca" to 40.0,
            "zawieszenia" to 50.0,
        )
        assertEquals(intervalsByKeyword.size, SeedData.serviceTasks.size)
        intervalsByKeyword.forEach { (keyword, interval) ->
            val task = SeedData.serviceTasks.single { it.title.contains(keyword) }
            assertEquals("interval for $keyword", interval, task.intervalMth, 0.0)
            assertTrue(task.isActive)
            assertEquals(0.0, task.lastDoneMth, 0.0)
        }
    }

    @Test
    fun `service task categories map to known enum values`() {
        SeedData.serviceTasks.forEach { entity ->
            assertEquals(entity.category, ServiceCategory.valueOf(entity.category).name)
        }
    }
}
