package com.xb88.rider.data.local

import com.xb88.rider.data.local.entity.BikeProfileEntity
import com.xb88.rider.data.local.entity.ServiceTaskEntity
import com.xb88.rider.domain.model.ServiceCategory

/** Verified Asix XB-88 300cc (AGB40-1) factory defaults, written on first launch. */
object SeedData {

    val bikeProfile = BikeProfileEntity(
        model = "Asix XB-88 300cc",
        platformCode = "AGB40-1",
        engine = "Zongshen PR300 (175FMN) - 271.3 cc, 4T, chłodzony powietrzem",
        power = "22 KM",
        gearbox = "Manualna 5-biegowa (1-N-2-3-4-5)",
        carburetor = "Nibbi PE30 (PE N30 C, ssanie ręczne)",
        ignition = "CDI",
        oilSpec = "Półsyntetyk 10W-40 4T JASO MA2 (~1.0 L - sprawdź na bagnecie/okienku)",
        tankCapacityL = 6.0,
        electricalSystem = "12V",
        batterySpec = "12V 5Ah",
        mainFuse = "10A",
        frontTire = "80/100-21\"",
        rearTire = "90/100-18\"",
        frontPressureBar = 1.3,
        rearPressureBar = 1.3,
        chainSpec = "520H",
        dimensions = "Długość 2100 mm | Siedzisko 910 mm | Udźwig 140 kg | Waga ~116.5-120 kg",
        valveClearanceNote = "Zweryfikuj z instrukcją głowicy (typowo 175FMN 2V: ssący ~0.06mm, " +
            "wydech ~0.08mm na zimno)",
        currentMth = 0.0,
        currentKm = 0.0,
        notes = "",
    )

    val serviceTasks = listOf(
        task("Wymiana oleju (10W-40 JASO MA2)", ServiceCategory.ENGINE, 12.0,
            "Przy każdej wymianie wyczyść sitko olejowe."),
        task("Filtr powietrza (mycie i nasączenie)", ServiceCategory.AIR_INTAKE, 5.0,
            "Kontrola po każdej jeździe w kurzu lub błocie."),
        task("Łańcuch 520H (czyszczenie, smarowanie, naciąg)", ServiceCategory.DRIVETRAIN, 1.0,
            "Po każdej jeździe."),
        task("Kontrola śrub (bolt check)", ServiceCategory.CHASSIS, 5.0,
            "Mocowania silnika, zębatka, szprychy, lampa LED, płyta pod silnikiem, handbary."),
        task("Luz zaworowy", ServiceCategory.ENGINE, 25.0,
            "Na zimnym silniku. Zweryfikuj wartości z instrukcją głowicy."),
        task("Świeca zapłonowa", ServiceCategory.IGNITION, 40.0,
            "Kolor izolatora = diagnostyka mieszanki Nibbi PE30."),
        task("Serwis zawieszenia (olej w lagach USD, łożyska)", ServiceCategory.SUSPENSION, 50.0,
            "Minimum raz w sezonie."),
    )

    private fun task(title: String, category: ServiceCategory, intervalMth: Double, notes: String) =
        ServiceTaskEntity(
            title = title,
            category = category.name,
            intervalMth = intervalMth,
            intervalDays = null,
            lastDoneMth = 0.0,
            lastDoneEpochMillis = null,
            notes = notes,
            isActive = true,
        )
}
