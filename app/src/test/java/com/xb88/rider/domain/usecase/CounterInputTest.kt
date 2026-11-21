package com.xb88.rider.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class CounterInputTest {

    @Test
    fun `valid input is parsed and rounded to a tenth`() {
        assertEquals(CounterInput.Result.Valid(12.6, 340.0), CounterInput.validate("12,56", "340"))
        assertEquals(CounterInput.Result.Valid(0.0, 0.0), CounterInput.validate("0", "0"))
    }

    @Test
    fun `invalid mth is reported before km`() {
        assertEquals(CounterInput.Result.InvalidMth, CounterInput.validate("-1", "abc"))
        assertEquals(CounterInput.Result.InvalidMth, CounterInput.validate("100001", "0"))
        assertEquals(CounterInput.Result.InvalidMth, CounterInput.validate("", "0"))
    }

    @Test
    fun `invalid km is reported`() {
        assertEquals(CounterInput.Result.InvalidKm, CounterInput.validate("10", "-5"))
        assertEquals(CounterInput.Result.InvalidKm, CounterInput.validate("10", "1000001"))
    }

    @Test
    fun `addMth avoids floating point drift and clamps to range`() {
        assertEquals(0.3, CounterInput.addMth(0.1, 0.2), 0.0)
        assertEquals(13.5, CounterInput.addMth(12.0, 1.5), 0.0)
        assertEquals(CounterInput.MAX_MTH, CounterInput.addMth(CounterInput.MAX_MTH, 2.0), 0.0)
    }
}
