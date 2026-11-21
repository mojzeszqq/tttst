# Asix XB-88 300cc (AGB40-1) — Kompleksowy Blueprint Aplikacji Android (Raport Multi-Agentowy)

Ten dokument łączy zweryfikowaną wiedzę techniczną o Twoim konkretnym egzemplarzu motocykla **Asix XB-88 300cc** (potwierdzoną na podstawie zdjęć motocykla, gaźnika i oficjalnej tabeli technicznej z instrukcji Asix), analizę możliwości integracji sprzętowej, projekt UX/UI dostosowany do realiów off-roadu oraz architekturę Androida przygotowaną specjalnie pod współpracę z **Claude Code**.

---

## Agent 1: Inżynier Motocyklowy & Mechanik Off-Road (Zweryfikowana Baza Wiedzy XB-88 300cc)

Dane w tabeli poniżej zostały zaktualizowane i **zweryfikowane na podstawie Twojej instrukcji obsługi Asix (str. 12 — Model `AGB40-1`) oraz zdjęć fizycznego egzemplarza**.

### 1.1. Zweryfikowana specyfikacja techniczna (Asix XB-88 300cc / AGB40-1)

| Parametr | Zweryfikowana wartość (z instrukcji i zdjęć) | Znaczenie dla aplikacji |
| :--- | :--- | :--- |
| **Model / Kod platformy** | **Asix XB-88 300cc** (kod fabryczny: **`AGB40-1`**) | Oficjalna nazwa i kod platformy do zamawiania części i w karcie pojazdu. |
| **Typ pojazdu** | Cross / Enduro off-road (**brak homologacji drogowej**) | Aplikacja musi działać w **100% offline** (lasy, żwirownie, bezdroża), bez nawigacji drogowej — zamiast niej ślady GPX i mapy terenowe. |
| **Silnik** | **Zongshen PR300 (`175FMN`)**, 1-cylindrowy, 4T, **chłodzony powietrzem** | 100% potwierdzone chłodzenie powietrzem (brak czujnika płynu/chłodnicy). Wyższa wrażliwość termiczna w wolnym terenie — kluczowy tracking motogodzin i oleju. |
| **Pojemność / Moc** | $271{,}3\text{ cm}^3$ / **$22\text{ KM}$** (instrukcja podaje też wariant $250\text{ cm}^3$ `172FMM` / $19\text{ KM}$) | Domyślny profil motocykla w bazie aplikacji (`175FMN`, $22\text{ KM}$). |
| **Skrzynia biegów** | Manualna, 5-biegowa (`1-N-2-3-4-5`) | Przydatne w kalkulatorze przełożeń (zębatki). |
| **Gaźnik i zapłon** | **Oryginalny Nibbi PE30** (oznaczenie na korpusie: `PE / N30 [C] ITBEI-B1106`, logo `NB` na komorze pływakowej), zapłon **CDI** | **Potwierdzony brak EFI / OBD-II / CAN.** Świetna wiadomość serwisowa: oryginalny Nibbi PE30 to bardzo wdzięczny gaźnik okrągłoprzepustnicowy ($30\text{ mm}$) z łatwym dostępem do dysz — dedykowany dziennik regulacji PE30 będzie w aplikacji strzałem w dziesiątkę. |
| **Instalacja elektryczna** | **$12\text{ V}$**, akumulator **$12\text{ V } 5\text{ Ah}$**, **bezpiecznik główny `10A`**, rozruch elektryczny + nożny | Krytyczne dane do zakładki „Ściągawka awaryjna” (warto wozić zapasowy bezpiecznik `10A`!) oraz dla przyszłego woltomierza BLE. |
| **Olej silnikowy** | **Półsyntetyk `10W-40`** (zalecenie z tabeli Asix; dla mokrego sprzęgła wymagana norma **`JASO MA2`**) | Domyślny wpis w karcie serwisowej wymiany oleju. |
| **Koła i opony** | Przód **`80/100-21"`** / Tył **`90/100-18"`**, felgi aluminiowe z trzymakami opon | Zapisane dokładne rozmiary opon w profilu motocykla. |
| **Zalecane ciśnienie opon** | Fabrycznie wg instrukcji: **`1.3 bar` przód / `1.3 bar` tył** | W aplikacji `1.3 bar` jako wartość referencyjna (fabryczna) + własne presety terenowe (np. $0{,}9\text{--}1{,}0\text{ bar}$ w piach/błoto). |
| **Napęd** | **Łańcuch `520H`** (na zdjęciu złoty łańcuch) | Zapisane w profilu; przypomnienia o smarowaniu i naciągu po każdej jeździe. |
| **Pojemność zbiornika paliwa** | **`6 litrów`** (półprzezroczysty biały bak na zdjęciu!) | **Kluczowa korekta względem ofert z sieci!** Dokładne $6\text{ l}$ pozwala realistycznie liczyć zasięg w terenie i alert niskiego poziomu paliwa. |
| **Masa / Udźwig / Wymiary** | Waga $106\text{--}120\text{ kg}$ (300cc z rozrusznikiem ok. $116{,}5\text{--}120\text{ kg}$), maks. udźwig **$140\text{ kg}$**, długość **$2100\text{ mm}$**, siedzisko **$910\text{ mm}$** | Pełna karta danych technicznych dostępna w aplikacji offline. |
| **Prędkość maks. / Hałas** | wg instrukcji do $120\text{ km/h}$ (zależnie od zębatek), poziom hałasu $\le 80\text{ dB(A)}$ | Informacje referencyjne w karcie motocykla. |
| **Wyposażenie widoczne na egzemplarzu** | Malowanie **pomarańczowo-czarno-białe (Asix Orange)**, lagi USD, tarcza wave, lampa LED, handbary Asix, płyta pod silnikiem, fabryczne USB-A/USB-C | Motyw UI aplikacji może wykorzystać kolor **Asix Orange** (`#FF5A00`) jako kolor akcentowy! |

### 1.2. Pozostałe drobiazgi do sprawdzenia (opcjonalna checklista uzupełniająca)

Większość niewiadomych już rozstrzygnęliśmy! Zostały tylko 4 detale, które warto sprawdzić przy okazji (aplikacja i tak pozwoli je w dowolnej chwili edytować):

1. **Zachowanie gniazd USB-A / USB-C:** sprawdź, czy telefon zaczyna się ładować dopiero po przekręceniu kluczyka w stacyjce, czy również na wyłączonym zapłonie. Jeśli po stacyjce — aplikacja może **automatycznie wykrywać uruchomienie motocykla** i startować licznik sesji!
2. **Tabela przeglądów i luzy zaworowe z dalszych stron instrukcji:** jeśli na kolejnych stronach Twojej papierowej instrukcji Asix jest tabela interwałów serwisowych lub podana pojemność oleju w litrach i luzy zaworowe — możesz podrzucić zdjęcie, a wpiszemy je co do milimetra do pre-seed bazy.
3. **Liczba zębów na zębatkach (przód / tył):** przydatne, jeśli będziesz chciał korzystać z kalkulatora przełożeń.
4. **Rzeczywista pojemność oleju przy wymianie:** dla `175FMN` to zwykle ok. $0{,}9\text{--}1{,}1\text{ l}$ (weryfikowane okienkiem/bagnetem).

### 1.3. Domyślny harmonogram serwisowy (szablony w aplikacji)

*Wartości startowe dla chłodzonych powietrzem 4T `175FMN` w jeździe terenowej — każdy interwał jest edytowalny w aplikacji.*

* **Olej silnikowy (Półsyntetyk `10W-40` 4T `JASO MA2`):**
  * Docieranie (nowy motocykl): po ok. $1\text{--}2\text{ mth}$, potem $5\text{ mth}$, następnie regularnie co **$10\text{--}15\text{ mth}$** (w ciężkim błocie/piasku bliżej $10\text{ mth}$). Czyszczenie sitka olejowego przy każdej wymianie.
* **Filtr powietrza (gąbkowy w airboxie):**
  * Kontrola po każdej jeździe w kurzu; mycie i nasączanie olejem do filtrów co **$3\text{--}5\text{ mth}$** (lub po każdej bardzo pylistej/błotnistej wyprawie).
* **Łańcuch napędowy (`520H`):**
  * Czyszczenie, smarowanie i kontrola naciągu po **każdej jeździe**.
* **Kontrola śrub i połączeń („bolt check”):**
  * Co **$5\text{ mth}$** — szczególnie mocowania silnika, zębatki tylnej, szprychy, mocowania lampy LED, osłony silnika i handbarów.
* **Luzy zaworowe (na zimnym silniku):**
  * Kontrola po dotarciu (ok. $5\text{--}10\text{ mth}$), następnie co **$20\text{--}30\text{ mth}$**. *(Orientacyjnie dla silników `172FMM/175FMN` 2V: ssący ok. $0{,}05\text{--}0{,}07\text{ mm}$, wydechowy ok. $0{,}07\text{--}0{,}10\text{ mm}$ — zawsze zweryfikować z instrukcją głowicy!)*
* **Świeca zapłonowa:** kontrola stanu i koloru izolatora (idealna diagnostyka mieszanki gaźnika **Nibbi PE30**) co **$15\text{--}20\text{ mth}$**, wymiana co ok. **$40\text{--}50\text{ mth}$**.
* **Serwis zawieszenia (olej w lagach USD, uszczelniacze) i smarowanie łożysk (kiwaczka, wahacz, główka ramy):**
  * Minimum **raz w sezonie** lub co **$40\text{--}50\text{ mth}$**.
* **Punkty wrażliwe XB-88 do uwzględnienia w checkliście inspekcji:**
  * Uchwyty lampy LED przy uderzeniu gałęzią lub glebie.
  * Zapasowy bezpiecznik **`10A`** w narzędziówce.
  * Kontrola wycięcia/naciągu łańcucha i dokręcenia śrub zębatki.

---

## Agent 2: Ekspert Elektroniki, Telemetrii i Hardware'u

Ponieważ XB-88 300cc (`AGB40-1`) pracuje na gaźniku **Nibbi PE30** i zapłonie **CDI** (bez OBD-II), architekturę telemetrii projektujemy warstwowo — pełna funkcjonalność od 1. dnia bez dodatkowych kosztów, z czystym interfejsem na przyszłą rozbudowę.

### 2.1. Trzy poziomy integracji sprzętowej

#### Poziom 1: Zero dodatkowego sprzętu (MVP — „Smartfon + USB w XB-88”)
* **GPS telefonu:** prędkość, dystans, przewyższenia, ślad trasy GPX, czas jazdy w ruchu vs postoju.
* **IMU (Akcelerometr + Żyroskop + Magnetometr):**
  * Kąt nachylenia podjazdu/zjazdu (*pitch*) i przechyłu bocznego (*roll*).
  * Przeciążenia (hamowanie, skoki).
  * **Detekcja upadku (Crash Detection):** wykrycie impulsu $G$ + brak ruchu przez $45\text{ s}$ -> alarm dźwiękowy -> opcjonalny SMS SOS z koordynatami GPS.
* **Wykorzystanie fabrycznego USB-C / USB-A:** nasłuchiwanie `ACTION_POWER_CONNECTED` / `ACTION_POWER_DISCONNECTED`. Jeśli gniazdo w XB-88 działa po stacyjce, aplikacja może automatycznie proponować start sesji i pilnować aktywności ekranu.

#### Poziom 2: Gotowe akcesoria Bluetooth Low Energy (BLE) — bez lutowania
* **Przycisk multimedialny BLE na kierownicę (ok. $30\text{--}60\text{ zł}$):** przełączanie widoków, stawianie waypointów lub pauza nagrania bez puszczania kierownicy.
* **Zewnętrzny odbiornik GPS BLE $10\text{ Hz}$:** wyższa dokładność śladu w gęstym lesie.
* **Czujniki BLE TPMS:** podgląd ciśnienia opon względem bazowych `1.3 bar` lub presetów terenowych.

#### Poziom 3: Projekt DIY „Smart XB88 Box” (ESP32-C3 + BLE GATT) — przyszła rozbudowa
Mała, wodoodporna puszka z mikrokontrolerem **ESP32-C3** podłączona do instalacji $12\text{ V}$ (za bezpiecznikiem `10A`):
* **RPM i prawdziwe motogodziny:** cewka indukcyjna (kilka zwojów przewodu na przewodzie WN świecy) lub sygnał z modułu CDI.
* **Temperatura głowicy (CHT):** czujnik termopary/NTC w podkładce pod świecę zapłonową — kluczowe zabezpieczenie silnika chłodzonego powietrzem w wolnym, błotnistym terenie.
* **Napięcie akumulatora $12\text{ V } 5\text{ Ah}$ i ładowania:** dzielnik rezystorowy na wejściu ADC ESP32 (kontrola regulatora napięcia i kondycji akumulatora przed wyjazdem).
* **Zasada architektoniczna:** interfejs `TelemetryProvider` w Kotlinie rozdziela źródło danych od ekranów UI, więc dodanie ESP32 w przyszłości nie wymaga przebudowy aplikacji.

### 2.2. Krytyczna uwaga praktyczna: Wibracje silnika `175FMN`
Jednocylindrowy silnik 300cc generuje wibracje potrafiące uszkodzić stabilizację optyczną (OIS) w aparacie głównego smartfona.
* **Rekomendacja:** uchwyt z tłumikiem drgań (*Vibration Dampener*) **ALBO** osobny, niedrogi pancerny smartfon z Androidem (IP68) zamontowany na stałe jako komputer pokładowy XB-88.

---

## Agent 3: Product Manager & UX/UI Designer (Koncepcja Aplikacji **XB88 Rider**)

### 3.1. Zasady UX „Off-Road First” i Stylistyka Asix Orange
1. **Obsługa w rękawicach (Glove-Friendly UI):** strefy dotykowe $\ge 56\text{ dp}$ (w kokpicie jazdy $64\text{--}72\text{ dp}$).
2. **Dwa motywy kolorystyczne inspirowane malowaniem Twojego XB-88:**
   * *Sun High-Contrast* (białe tło, głęboka czerń tekstu, akcent **Asix Orange `#FF5A00`**),
   * *Night Enduro OLED* (czysta czerń `#000000`, wysoki kontrast bieli i **Asix Orange `#FF6B00`** — oszczędza baterię i nie oślepia w lesie po zmroku).
3. **100% Offline-First:** zero logowania, zero chmury, pełna kopia zapasowa do pliku JSON.
4. **Blokada deszczowa (Rain Lock):** blokada dotyku na kokpicie (odblokowanie przytrzymaniem przez $2\text{ s}$), aby krople deszczu lub błoto nie naciskały przycisków.

### 3.2. Moduły aplikacji (Podział na Fazy)

#### Faza 1 — MVP (Rdzeń od 1. dnia)
1. **Garaż & Karta `AGB40-1`:**
   * Prekonfigurowany profil **Asix XB-88 300cc (`AGB40-1` / `175FMN`)** z wszystkimi zweryfikowanymi parametrami (wszystkie edytowalne), aktualny stan `MTH` i `km` oraz szybka **ściągawka awaryjna offline** (olej `10W-40` półsyntetyk, bezpiecznik `10A`, aku `12V 5Ah`, opony `80/100-21` / `90/100-18`, ciśnienie `1.3 bar`, łańcuch `520H`, bak `6 L`, gaźnik `Nibbi PE30`).
2. **Książka Serwisowa MTH:**
   * Zadania cykliczne z kolorowymi paskami postępu (`Zielony` -> `Żółty` $\le 2\text{ mth}$ -> `Czerwony po terminie`).
   * Szybkie logowanie wykonanego serwisu (MTH, koszt w PLN, notatka, opcjonalne zdjęcie np. koloru świecy lub paragonu).
3. **Kokpit Terenowy (Ride Dashboard) + Nagrywanie Tras:**
   * Duży prędkościomierz GPS, dystans sesji, czas jazdy, wysokość n.p.m., przechyłomierz IMU (*pitch*/*roll*), status zasilania USB, Rain Lock.
   * Nagrywanie w tle (`Foreground Service`), historia tras z podglądem na mapie OSMDroid i **eksportem do `.gpx`**.
4. **Checklista Przed Jazdą (Pre-Ride Check):**
   * Szybka lista pod XB-88: poziom paliwa (rzut oka na biały bak $6\text{ l}$), olej, naciąg łańcucha `520H`, ciśnienie w kołach, śruby lampy LED / płyty silnika, zapasowy bezpiecznik `10A` i podstawowe klucze.

#### Faza 2 — Tuning (Zawieszenie + Nibbi PE30), Paliwo i Bezpieczeństwo
5. **Dziennik Ustawień (Setup Notebook):**
   * **Zawieszenie:** zapis klików (Przód USD: kompresja/odbicie; Tył: kompresja/odbicie/preload) w profilach terenowych.
   * **Gaźnik Nibbi PE30 (`N30 C`):** zapis dyszy głównej (main jet), dyszy wolnych obrotów (pilot jet), rowka iglicy, obrotów śruby składu mieszanki (wyjściowo ok. $1{,}5$ obrotu od wkręcenia), temperatury otoczenia i koloru świecy.
   * **Opony i przełożenia:** profile ciśnień (Fabryczne `1.3 bar` vs Las/Błoto/Piasek) i notatki zębatek.
6. **Dziennik Paliwa ($6\text{ l}$ bak) i Kosztów:**
   * Kalkulator spalania ($\text{l/mth}$ i $\text{l/100 km}$), szacowany pozostały zasięg dla $6\text{-litrowego}$ baku oraz podsumowanie kosztów eksploatacji.
7. **Bezpieczeństwo (SOS SMS):**
   * Przycisk szybkiego SMS-a z aktualnymi współrzędnymi GPS i linkiem do mapy (działa po samej sieci GSM bez internetu mobilnego).

#### Faza 3 — Mapy Offline & Hardware BLE (Pro)
8. **Mapy topograficzne offline + własne punkty POI** (trudne podjazdy, leśne stacje paliw, myjki).
9. **Integracja BLE:** pilot na kierownicę, TPMS, opcjonalny moduł DIY ESP32 (RPM, temperatura głowicy CHT, woltomierz $12\text{ V}$).

---

## Agent 4: Architekt Androida (Stack Technologiczny)

* **Język:** **Kotlin 2.x** (natywny Android — pełna kontrola nad `Foreground Service`, GPS, IMU, USB i BLE).
* **UI:** **Jetpack Compose** + **Material 3** (motyw **Asix Orange** w wariantach `SunHighContrast` i `NightEnduroOled`).
* **Architektura:** **MVVM + Clean Architecture w jednym module (`:app`)** z podziałem pakietów (`data`, `domain`, `ui`, `service`, `hardware`) — szybkie budowanie i brak problemów z wielomodułowym Gradle w Claude Code.
* **Baza danych:** **Room (SQLite)** + Coroutines/`Flow` + **Jetpack DataStore** (preferencje).
* **DI:** **Hilt**.
* **Nawigacja:** **Navigation Compose**.
* **Mapy i GPX:** **OSMDroid** (100% darmowy OpenStreetMap bez kluczy API Google Maps) + lekki eksporter/parser GPX 1.1 w czystym Kotlinie.
* **Backup:** `kotlinx.serialization` (eksport/import JSON przez Storage Access Framework).

### Struktura Bazy Danych (Room Entities)

```text
BikeProfile (id, model="Asix XB-88 300cc", platformCode="AGB40-1", engineCode="Zongshen PR300 / 175FMN", currentMth, currentKm, oilSpec="Półsyntetyk 10W-40 JASO MA2", tankCapacityL=6.0, batterySpec="12V 5Ah", fuseSpec="10A", frontTire="80/100-21", rearTire="90/100-18", defaultPressureBar=1.3, chainSpec="520H", carbModel="Nibbi PE30 (N30 C)", ...)
ServiceTask (id, title, category, intervalMth, intervalDays, lastDoneMth, lastDoneDate, notes, isActive)
ServiceLog (id, taskId?, date, mthAtService, kmAtService, title, description, costPln, photoUri?)
RideSession (id, startTime, endTime, durationSec, engineMthAdded, distanceMeters, maxSpeedKmh, avgSpeedKmh, elevationGainM, notes, gpxPath?)
TrackPoint (id, sessionId, timestamp, lat, lon, altitudeM, speedKmh, pitchDeg, rollDeg)
SetupPreset (id, type[SUSPENSION|CARB_PE30|TIRES], name, terrainTag, jsonParams, notes, updatedAt)
FuelLog (id, date, mth, liters, costPln, isFullTank)
ChecklistItem (id, title, isRequired, orderIndex)
```

---

## Agent 5: Inżynier AI / Claude Code Lead (Strategia Budowy z Claude Code)

### 5.1. Przygotowanie środowiska przed uruchomieniem Claude Code
1. Zainstaluj **Android Studio** (najnowsza wersja stabilna) z Android SDK i JDK 17/21.
2. Wygeneruj czysty szkielet w Android Studio: `New Project -> Empty Activity (Jetpack Compose)`, `Minimum SDK: API 26` (lub `28`), Kotlin DSL (`build.gradle.kts`).
3. Zweryfikuj budowanie w terminalu: `./gradlew assembleDebug`.
4. Zainicjuj Git (`git init && git add . && git commit -m "Initial Android template"`).
5. Wrzuć plik `CLAUDE.md` do katalogu głównego projektu.

### 5.2. Pętla pracy z Claude Code (Milestones M1–M6)
Po każdym etapie Claude Code ma obowiązek uruchomić `./gradlew assembleDebug` oraz `./gradlew testDebugUnitTest`, a Ty robisz commit w Git:
* **M1:** Fundament (Hilt, Room, DataStore, Nawigacja), motyw **Asix Orange** (`SunHighContrast` / `NightEnduroOled`), pre-seed zweryfikowanych danych `Asix XB-88 300cc (AGB40-1)`, ekran **Garaż + Ściągawka awaryjna**.
* **M2:** **Książka Serwisowa MTH** (paski postępu, logowanie serwisu, koszty, zdjęcia) + **Checklista Przed Jazdą**.
* **M3:** **Kokpit Terenowy** (`TelemetryProvider`, GPS, IMU przechyłomierz, wykrywanie USB, Rain Lock).
* **M4:** **Nagrywanie Tras w Tle** (`Foreground Service`), lista tras, mapa OSMDroid i eksport `.gpx`.
* **M5:** **Dziennik Ustawień** (kliki zawieszenia USD/tył, szczegółowy log **Nibbi PE30**, presety ciśnień opon) + **Backup JSON**.
* **M6:** **Dziennik Paliwa ($6\text{ l}$ bak) i Kosztów** + **Przycisk SOS SMS**.

### 5.3. Pomysły rozwojowe na kolejne wersje
1. **Drzewko Diagnostyczne Offline dla `175FMN` + `Nibbi PE30`:** szybka pomoc w lesie (*„Nie kręci rozrusznik -> sprawdź bezpiecznik 10A i napięcie aku 12V 5Ah”*, *„Dławi się przy dodaniu gazu -> sprawdź iglicę / dyszę PE30 i odpowietrzenie korka”*).
2. **Dźwiękowy obrotomierz biegu jałowego (FFT z mikrofonu):** orientacyjny pomiar wolnych obrotów przy regulacji śruby biegu jałowego w Nibbi PE30 bez podłączania kabli.
3. **Asystent „Po jeździe w błocie/kurzu”:** szybkie pytanie po zakończeniu trasy o przypomnienie mycia filtra i smarowania łańcucha `520H`.