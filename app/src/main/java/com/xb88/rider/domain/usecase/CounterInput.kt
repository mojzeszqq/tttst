package com.xb88.rider.domain.usecase

import com.xb88.rider.domain.model.parseDecimal
import kotlin.math.roundToLong

/** Validation and arithmetic for the engine-hour (MTH) and odometer counters. */
object CounterInput {
    const val MAX_MTH = 100_000.0
    const val MAX_KM = 1_000_000.0

    sealed interface Result {
        data class Valid(val mth: Double, val km: Double) : Result
        data object InvalidMth : Result
        data object InvalidKm : Result
    }

    /** Parses user input (accepts `12.5` and `12,5`), rounding both counters to 0.1. */
    fun validate(mthInput: String, kmInput: String): Result {
        val mth = parseDecimal(mthInput)?.takeIf { it <= MAX_MTH } ?: return Result.InvalidMth
        val km = parseDecimal(kmInput)?.takeIf { it <= MAX_KM } ?: return Result.InvalidKm
        return Result.Valid(mth = roundTenth(mth), km = roundTenth(km))
    }

    /** Adds [delta] hours to [current], clamped to the valid range and rounded to 0.1 mth. */
    fun addMth(current: Double, delta: Double): Double =
        roundTenth((current + delta).coerceIn(0.0, MAX_MTH))

    private fun roundTenth(value: Double): Double = (value * 10).roundToLong() / 10.0
}
