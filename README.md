# XB88 Rider

Offline-first Android companion app for the **Asix XB-88 300cc (AGB40-1)** off-road motorcycle.
Project rules live in [`CLAUDE.md`](CLAUDE.md); the full product blueprint (Polish) is in
[`docs/BLUEPRINT.md`](docs/BLUEPRINT.md).

## Status

- [x] Milestone 1 — Foundation & Garage (Hilt, Room, DataStore, Navigation Compose,
      kotlinx.serialization, SunHighContrast / NightEnduroOled themes, pre-seeded AGB40-1
      profile and service tasks, Garage screen with editable specs, MTH dialog and
      emergency cheat-sheet)
- [ ] Milestone 2 — Service Book & Pre-Ride Checklist
- [ ] Milestone 3 — Ride Dashboard & Sensors
- [ ] Milestone 4 — Background GPS recording, Tracks & GPX
- [ ] Milestone 5 — Setup Notebook & JSON backup
- [ ] Milestone 6 — Fuel/Cost log & Emergency SOS

## Build

Requires JDK 17+ and the Android SDK (platform 35). Point Gradle at the SDK with
`ANDROID_HOME` or a `local.properties` file containing `sdk.dir=/path/to/sdk`.

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
```

Toolchain: AGP 8.7.3, Kotlin 2.1.0, KSP 2.1.0-1.0.29, Compose BOM 2024.12.01, Gradle 8.11.1,
minSdk 26, targetSdk 35.
