package com.xb88.rider.domain.model

import com.xb88.rider.data.local.SeedData
import com.xb88.rider.data.local.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SpecFieldTest {

    private val bike = SeedData.bikeProfile.toDomain()

    @Test
    fun `parseDecimal accepts dot and Polish comma`() {
        assertEquals(1.3, parseDecimal("1.3")!!, 0.0)
        assertEquals(1.3, parseDecimal(" 1,3 ")!!, 0.0)
        assertEquals(6.0, parseDecimal("6")!!, 0.0)
    }

    @Test
    fun `parseDecimal rejects negative, blank and non-numeric input`() {
        assertNull(parseDecimal("-1"))
        assertNull(parseDecimal(""))
        assertNull(parseDecimal("abc"))
        assertNull(parseDecimal("NaN"))
        assertNull(parseDecimal("Infinity"))
    }

    @Test
    fun `formatDecimal drops trailing zero for whole numbers`() {
        assertEquals("6", formatDecimal(6.0))
        assertEquals("1.3", formatDecimal(1.3))
    }

    @Test
    fun `every field round-trips through valueOf and withValue`() {
        SpecField.entries.forEach { field ->
            val raw = bike.valueOf(field)
            if (raw.isBlank() && field == SpecField.NOTES) return@forEach
            assertEquals("field $field", bike, bike.withValue(field, raw))
        }
    }

    @Test
    fun `decimal fields are parsed and stored`() {
        assertEquals(0.9, bike.withValue(SpecField.FRONT_PRESSURE_BAR, "0,9")!!.frontPressureBar, 0.0)
        assertEquals(7.5, bike.withValue(SpecField.TANK_CAPACITY_L, "7.5")!!.tankCapacityL, 0.0)
        assertNull(bike.withValue(SpecField.REAR_PRESSURE_BAR, "dużo"))
    }

    @Test
    fun `text fields are trimmed and blank values rejected except notes`() {
        assertEquals("15A", bike.withValue(SpecField.MAIN_FUSE, "  15A ")!!.mainFuse)
        assertNull(bike.withValue(SpecField.MODEL, "   "))
        assertEquals("", bike.copy(notes = "old").withValue(SpecField.NOTES, " ")!!.notes)
    }
}
