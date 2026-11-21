package com.xb88.rider.domain.model

/** Editable specification fields of [BikeProfile]. MTH/km are edited via the quick MTH dialog. */
enum class SpecField(val kind: Kind) {
    MODEL(Kind.TEXT),
    PLATFORM_CODE(Kind.TEXT),
    ENGINE(Kind.TEXT),
    POWER(Kind.TEXT),
    GEARBOX(Kind.TEXT),
    CARBURETOR(Kind.TEXT),
    IGNITION(Kind.TEXT),
    OIL_SPEC(Kind.TEXT),
    TANK_CAPACITY_L(Kind.DECIMAL),
    ELECTRICAL_SYSTEM(Kind.TEXT),
    BATTERY(Kind.TEXT),
    MAIN_FUSE(Kind.TEXT),
    FRONT_TIRE(Kind.TEXT),
    REAR_TIRE(Kind.TEXT),
    FRONT_PRESSURE_BAR(Kind.DECIMAL),
    REAR_PRESSURE_BAR(Kind.DECIMAL),
    CHAIN(Kind.TEXT),
    DIMENSIONS(Kind.TEXT),
    VALVE_CLEARANCE(Kind.TEXT),
    NOTES(Kind.TEXT),
    ;

    enum class Kind { TEXT, DECIMAL }
}

fun BikeProfile.valueOf(field: SpecField): String = when (field) {
    SpecField.MODEL -> model
    SpecField.PLATFORM_CODE -> platformCode
    SpecField.ENGINE -> engine
    SpecField.POWER -> power
    SpecField.GEARBOX -> gearbox
    SpecField.CARBURETOR -> carburetor
    SpecField.IGNITION -> ignition
    SpecField.OIL_SPEC -> oilSpec
    SpecField.TANK_CAPACITY_L -> formatDecimal(tankCapacityL)
    SpecField.ELECTRICAL_SYSTEM -> electricalSystem
    SpecField.BATTERY -> batterySpec
    SpecField.MAIN_FUSE -> mainFuse
    SpecField.FRONT_TIRE -> frontTire
    SpecField.REAR_TIRE -> rearTire
    SpecField.FRONT_PRESSURE_BAR -> formatDecimal(frontPressureBar)
    SpecField.REAR_PRESSURE_BAR -> formatDecimal(rearPressureBar)
    SpecField.CHAIN -> chainSpec
    SpecField.DIMENSIONS -> dimensions
    SpecField.VALVE_CLEARANCE -> valveClearanceNote
    SpecField.NOTES -> notes
}

/**
 * Returns a copy with [field] set from raw user input, or null if the input is invalid
 * (blank required text, or a decimal that is not a finite non-negative number).
 */
fun BikeProfile.withValue(field: SpecField, raw: String): BikeProfile? {
    val text = raw.trim()
    if (field.kind == SpecField.Kind.DECIMAL) {
        val number = parseDecimal(text) ?: return null
        return when (field) {
            SpecField.TANK_CAPACITY_L -> copy(tankCapacityL = number)
            SpecField.FRONT_PRESSURE_BAR -> copy(frontPressureBar = number)
            SpecField.REAR_PRESSURE_BAR -> copy(rearPressureBar = number)
            else -> error("Unhandled decimal field $field")
        }
    }
    if (text.isEmpty() && field != SpecField.NOTES) return null
    return when (field) {
        SpecField.MODEL -> copy(model = text)
        SpecField.PLATFORM_CODE -> copy(platformCode = text)
        SpecField.ENGINE -> copy(engine = text)
        SpecField.POWER -> copy(power = text)
        SpecField.GEARBOX -> copy(gearbox = text)
        SpecField.CARBURETOR -> copy(carburetor = text)
        SpecField.IGNITION -> copy(ignition = text)
        SpecField.OIL_SPEC -> copy(oilSpec = text)
        SpecField.ELECTRICAL_SYSTEM -> copy(electricalSystem = text)
        SpecField.BATTERY -> copy(batterySpec = text)
        SpecField.MAIN_FUSE -> copy(mainFuse = text)
        SpecField.FRONT_TIRE -> copy(frontTire = text)
        SpecField.REAR_TIRE -> copy(rearTire = text)
        SpecField.CHAIN -> copy(chainSpec = text)
        SpecField.DIMENSIONS -> copy(dimensions = text)
        SpecField.VALVE_CLEARANCE -> copy(valveClearanceNote = text)
        SpecField.NOTES -> copy(notes = text)
        else -> error("Unhandled text field $field")
    }
}

/** Parses a non-negative decimal, accepting both `1.3` and the Polish `1,3`. */
fun parseDecimal(input: String): Double? {
    val value = input.trim().replace(',', '.').toDoubleOrNull() ?: return null
    return value.takeIf { it.isFinite() && it >= 0.0 }
}

/** Formats without a trailing `.0` for whole numbers: 6.0 -> "6", 1.3 -> "1.3". */
fun formatDecimal(value: Double): String =
    if (value % 1.0 == 0.0) value.toLong().toString() else value.toString()
