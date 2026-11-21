# CLAUDE.md — XB88 Rider (Asix XB-88 300cc / AGB40-1 Android Companion App)

## 1. Project Overview
You are building **XB88 Rider**, a native, **100% offline-first** Android companion app tailored specifically for the user's **Asix XB-88 300cc** off-road enduro/cross motorcycle.

### Verified Target Motorcycle Facts (From Official Manual & Owner Photos)
- **Model / Factory Platform Code:** `Asix XB-88 300cc` / **`AGB40-1`** (off-road only, no road homologation).
- **Color Scheme / Branding Inspiration:** Orange / Black / White (**Asix Orange** accent `#FF5A00` / `#FF6B00`).
- **Engine:** Zongshen PR300 (**`175FMN`**), single-cylinder, 4-stroke, **air-cooled**, 271.3 cc, **22 KM**, 5-speed manual (`1-N-2-3-4-5`), electric + kick start.
- **Carburetor & Ignition:** Confirmed genuine **Nibbi PE30** round-slide carburetor (body stamp: `PE / N30 C ITBEI-B1106`, `NB` logo on float bowl, manual choke) + **CDI** ignition.
  - **CRITICAL:** This motorcycle has **NO EFI, NO ECU, NO CAN bus, and NO OBD-II port**. NEVER assume or generate OBD-II / ELM327 code.
- **Electrical System:** `12V`, battery **`12V 5Ah`**, main fuse **`10A`**, built-in USB-A and USB-C power ports, LED headlight.
- **Engine Oil (Factory Spec):** Semi-synthetic **`10W-40`** (4T `JASO MA2` for wet clutch).
- **Fuel Tank:** **`6.0 L`** (translucent white tank — visually inspectable).
- **Tires & Factory Pressure:** Front **`80/100-21"`**, Rear **`90/100-18"`** (aluminum rims with rim locks). Factory recommended pressure: **`1.3 bar`** front / **`1.3 bar`** rear (riders often use lower presets off-road).
- **Drive Chain:** **`520H`**.
- **Suspension & Chassis:** USD front forks + rear shock (dual compression/rebound adjustment), seat height **`910 mm`**, length **`2100 mm`**, weight **`106–120 kg`** (~116.5–120 kg for 300cc e-start), max load **`140 kg`**, noise level `<= 80 dB(A)`.
- **Editable Defaults:** Pre-seed all verified XB-88 / AGB40-1 values above on first launch, while keeping **every specification field user-editable**.

## 2. Language & UX Requirements
- **Code, comments, git commits, technical identifiers:** English.
- **User-facing UI strings (`strings.xml`):** **Polish (`pl-PL`)**. Keep copy concise and natural for Polish off-road riders (e.g., "Motogodziny (mth)", "Luz zaworowy", "Filtr powietrza", "Kliki zawieszenia", "Gaźnik Nibbi PE30").
- **Off-Road UX Rules:**
  - **Glove-friendly:** All interactive buttons on riding/dashboard screens MUST be at least `56dp` (prefer `64dp–72dp` for primary actions).
  - **Two selectable themes (Asix Orange accent):**
    1. `SunHighContrast`: crisp white background, heavy dark typography, Asix Orange (`#FF5A00`) primary accent for maximum readability in direct sunlight.
    2. `NightEnduroOled`: pure `#000000` background, high-contrast white text, Asix Orange (`#FF6B00`) accent.
  - **100% Offline-First:** Zero mandatory internet calls, zero login/auth screens, zero cloud backend dependencies.
  - **Rain Lock:** Dashboard must include a touch-lock toggle (unlock via 2-second long-press) to prevent raindrops/mud from triggering accidental clicks.

## 3. Tech Stack & Architecture
- **Language:** Kotlin (2.x)
- **UI:** Jetpack Compose + Material 3
- **Architecture:** Clean Architecture + MVVM in a **single `:app` module** (keep Gradle simple and fast!).
- **DI:** Hilt
- **Local DB:** Room (SQLite) with Coroutines & `Flow`
- **Preferences:** Jetpack DataStore (Preferences)
- **Navigation:** Navigation Compose
- **Maps & GPX:** OSMDroid (free OpenStreetMap tiles, offline-capable) + custom lightweight GPX 1.1 XML exporter/importer in pure Kotlin (no Google Maps API key required).
- **Background Tracking:** Android Foreground Service (`foregroundServiceType="location"`) compliant with Android 14+ runtime permissions.
- **Backup:** `kotlinx.serialization` JSON export/import via Storage Access Framework (`ActivityResultContracts.CreateDocument` / `OpenDocument`).

### Package Structure (`com.xb88.rider`)
```text
com.xb88.rider/
  data/
    local/          # Room Database, DAOs, Entities, Pre-seed Callback
    preferences/    # DataStore repositories
    repository/     # Repository implementations
    gpx/            # GPX 1.1 generator & parser
    backup/         # JSON full DB export/import
  domain/
    model/          # Clean domain models
    usecase/        # Business logic (MTH calculations, service urgency, fuel range)
  hardware/
    telemetry/      # TelemetryProvider interface + PhoneSensorTelemetryProvider (IMU/GPS/USB)
    sensors/        # Pitch/Roll inclinometer, Crash detector (future), USB power receiver
  service/          # RideTrackingService (Foreground Service)
  ui/
    theme/          # SunHighContrast & NightEnduroOled themes (Asix Orange)
    navigation/     # NavGraph & BottomBar
    garage/         # Bike profile (AGB40-1), editable specs, emergency cheat-sheet
    servicebook/    # MTH counter, service task progress bars, logs, costs
    dashboard/      # Live off-road ride cockpit, rain lock, IMU tilt
    tracks/         # Recorded rides list, OSMDroid map preview, GPX export
    setup/          # Suspension clicker presets, Nibbi PE30 jetting log, tire pressures
    checklist/      # Pre-ride interactive checklist & emergency SOS SMS helper
```

## 4. Coding Rules & Hard Constraints
1. **No Hallucinated Dependencies:** Before adding a library to `gradle/libs.versions.toml` or `build.gradle.kts`, inspect existing project versions so Kotlin, KSP, AGP, and Compose BOM stay fully compatible.
2. **Hardware Abstraction:** Always consume live ride metrics via a `TelemetryProvider` interface (`Flow<TelemetrySnapshot>`) so a future BLE ESP32/TPMS provider can be plugged in without refactoring UI screens.
3. **Permission Safety:** Never crash on denied permissions. Gracefully degrade (if `ACCESS_FINE_LOCATION` is denied, Garage, Service Book, Setup Notebook, and Checklist must remain 100% usable).
4. **Unit Tests Required for Core Logic:** Write JUnit unit tests for:
   - Service interval & urgency status calculation (Green / Warning `<= 2.0 mth` / Overdue).
   - Fuel consumption (`L/mth`, `L/100km`) and estimated remaining range based on `6.0 L` tank.
   - GPX 1.1 XML serialization.
   - Pitch/roll low-pass filter smoothing.
   - JSON backup round-trip serialization.
5. **Keep Screen Awake Safely:** Keep screen awake ONLY while on the active Ride Dashboard screen (e.g., `DisposableEffect` on `LocalView` setting `keepScreenOn = true`).

## 5. Build & Verification Workflow (MANDATORY)
After implementing any feature or milestone, ALWAYS run:
```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
```
Do not consider a task complete until both commands succeed with exit code `0`. Fix all compiler errors and failing tests before reporting completion.

## 6. Implementation Roadmap (Milestones)
Execute one milestone at a time when prompted by the user:

- **Milestone 1 (Foundation & Garage):**
  - Configure Hilt, Room, DataStore, Navigation Compose, `kotlinx.serialization`.
  - Implement `SunHighContrast` and `NightEnduroOled` themes using Asix Orange accents.
  - Create Room database with pre-seeded **Asix XB-88 300cc (AGB40-1)** profile and default off-road service tasks (Oil `10W-40` every `12 mth`, Air filter `5 mth`, Chain `520H` every `1 mth`/ride, Bolt check `5 mth`, Valves `25 mth`, Spark plug `40 mth`, Suspension `50 mth`).
  - Build **Garage Screen** (editable bike specs, quick MTH update dialog, offline emergency cheat-sheet highlighting `10A` fuse, `12V 5Ah` battery, `10W-40` oil, `6 L` tank, `1.3 bar` tires).

- **Milestone 2 (Service Book & Pre-Ride Checklist):**
  - Build **Service Book Screen** with color-coded progress bars, "Log Service Done" dialog (mth, cost PLN, notes, optional photo URI), and full service history timeline.
  - Build **Pre-Ride Checklist Screen** with resettable checkboxes tailored to XB-88 (visual fuel check on white `6 L` tank, oil level, `520H` chain tension, tire pressure, LED headlight & skid plate bolts, spare `10A` fuse & tools).

- **Milestone 3 (Off-Road Ride Dashboard & Sensors):**
  - Implement `TelemetryProvider` using phone GPS + IMU (`SensorManager` with low-pass filter for pitch/roll) + USB charging status detection (`BatteryManager` / broadcast).
  - Build glove-friendly **Ride Dashboard Screen** (huge speed readout, trip distance, duration, altitude, pitch/roll inclinometer, USB power indicator, Rain Lock).

- **Milestone 4 (Background GPS Recording, Tracks & GPX):**
  - Implement `RideTrackingService` (`Foreground Service` with persistent notification showing speed & duration).
  - Save `RideSession` and `TrackPoint` records to Room; prompt user at ride end whether to add engine duration to bike MTH.
  - Build **Tracks Screen** with ride stats, OSMDroid map preview, and **Export to `.gpx`** via Android Share Sheet / SAF.

- **Milestone 5 (Setup Notebook & Backup):**
  - Build **Setup Notebook Screen** with tabs for:
    1. Suspension Clickers (Front USD Comp/Reb, Rear Comp/Reb/Preload) with terrain presets.
    2. **Nibbi PE30 (`N30 C`) Carburetor Jetting Log** (Main jet, pilot jet, needle clip position, air screw turns from seated, ambient temp, spark plug color).
    3. Tire Pressure Presets (Factory `1.3 bar` reference vs Mud/Sand/Hardpack) & Sprocket Notes.
  - Implement full **JSON Export / Import Backup** in Settings.

- **Milestone 6 (Fuel/Cost Log & Emergency SOS):**
  - Add Fuel Log (calculate `L/mth`, `L/100km`, and estimated remaining range for the `6.0 L` tank) and Total Ownership Cost summary.
  - Add Emergency SOS quick action (opens SMS composer pre-filled with current GPS coordinates & Google Maps link — works over basic GSM without mobile data).

## 7. Pre-Seeded Reference Data (Verified Asix XB-88 300cc / AGB40-1)
Use these verified defaults when pre-seeding the database on first launch:
- Model: `Asix XB-88 300cc`
- Platform Code: `AGB40-1`
- Engine: `Zongshen PR300 (175FMN) - 271.3 cc, 4T, chłodzony powietrzem`
- Power: `22 KM`
- Gearbox: `Manualna 5-biegowa (1-N-2-3-4-5)`
- Carburetor: `Nibbi PE30 (PE N30 C, ssanie ręczne)`
- Oil: `Półsyntetyk 10W-40 4T JASO MA2 (~1.0 L - sprawdź na bagnecie/okienku)`
- Fuel Tank: `6.0 L`
- Electrical: `12V | Akumulator 12V 5Ah | Bezpiecznik główny 10A`
- Tires: `Przód 80/100-21" | Tył 90/100-18" (trzymaki opon)`
- Factory Tire Pressure: `1.3 bar przód / 1.3 bar tył`
- Chain: `520H`
- Dimensions & Load: `Długość 2100 mm | Siedzisko 910 mm | Udźwig 140 kg | Waga ~116.5-120 kg`
- Valve Clearance Note: `Zweryfikuj z instrukcją głowicy (typowo 175FMN 2V: ssący ~0.06mm, wydech ~0.08mm na zimno)`