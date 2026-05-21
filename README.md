# Namma Metro Sahaya

**First-Timer's Metro Guide for Rural Visitors to Bengaluru**
MindMatrix VTU Internship — Project #73 (Android + Kotlin)

A friendly Android app that walks first-time Metro users through every step
of riding the Bengaluru Namma Metro: searching a route, switching at the
Majestic interchange, finding the right exit gate, and using the token
machine — all with bilingual UI (English / Kannada) and offline support.

---

## How to open and run

1. Open **Android Studio** (Hedgehog or later).
2. Choose **Open** → select the `NammaMetroSahaya/` folder.
3. Let Gradle sync finish (it will download Gradle 8.5 and dependencies the first time — needs internet **only on first sync**, after that the app runs fully offline).
4. Connect a phone over USB (with Developer Options → USB Debugging enabled) **or** start an emulator (API 24+).
5. Click the green **Run** ▶ button.

The launcher icon **Namma Metro Sahaya** appears on the phone. Tap it — splash
screen → home → search a route.

---

## Demo flow for your lecturer

| # | Action | What to point out |
| - | ------ | ----------------- |
| 1 | Open app | Splash screen with branded logo |
| 2 | Tap **ಕನ್ನಡ / English** | Live language toggle — entire UI re-renders in Kannada with bigger 20sp font |
| 3 | Pick **Whitefield (Kadugodi)** → **Silk Institute** | Cross-line route across both Purple and Green lines |
| 4 | Tap **FIND ROUTE** | Route screen: fare, time, stops, interchange card highlights Majestic |
| 5 | Show stops list | Each stop dotted in its line colour; an amber **INTERCHANGE** badge marks the switch |
| 6 | Show **Route computed in Xms** | Pathfinding (BFS) finishes well under the 500ms NFR-01 target |
| 7 | Tap **START VISUAL GUIDE** | Stepper UI: token → tap gate → platform → train → interchange → exit |
| 8 | At final step, tap **FIND MY EXIT** | Exit Finder lists Gate 1 → KSRTC, Gate 2 → BMTC, Gate 3 → City Railway, Gate 4 → SBS Road |
| 9 | Back to home → tap **How to buy a token?** | Standalone Token Machine guide |
| 10 | Toggle **Airplane Mode ON**, restart app | Orange **OFFLINE** banner appears; route + visual guide still work |

---

## Feature → File map (for code walkthrough)

| Feature (PRD) | Where it lives |
| ------------- | -------------- |
| FR-01 Station search & route input | `HomeActivity.kt` + `activity_home.xml` |
| FR-02 Visual step-by-step guide | `StepperActivity.kt` + `StepData.kt` |
| FR-03 Interchange highlight | `Pathfinder.kt` (detection) + `activity_route.xml` (UI card) |
| FR-04 Exit Finder | `ExitFinderActivity.kt` + `ExitData.kt` |
| FR-05 Fare & travel time | `FareCalculator.kt` (slab-based) |
| FR-06 Token machine guide | `TokenMachineActivity.kt` |
| FR-07 Offline visual guide | All data is **bundled in Kotlin objects** — no network needed |
| FR-08 Kannada large-font UI | `LocaleHelper.kt` + `values-kn/strings.xml` + `values-kn/dimens.xml` |
| Pathfinding (BFS, Nodes & Edges) | `MetroNetwork.kt` + `Pathfinder.kt` |
| GenAI journey tips | `StepData.journeyTips()` (rule-based contextual tips) |
| Splash screen | `SplashActivity.kt` + `activity_splash.xml` |

---

## Architecture (one paragraph)

Single-app module. Six Activities navigate linearly: Splash → Home → Route →
Stepper → ExitFinder, with TokenMachine as a side branch from Home. The
Metro network is modelled as an **undirected graph** in `MetroNetwork.kt`
(Nodes = `Station`, Edges = adjacency on a line plus a single shared
Majestic node where Purple and Green meet). `Pathfinder.kt` runs BFS for the
shortest path by number of stops, then walks the resulting station sequence
to detect line changes and flag the interchange. All station data, exit
maps, and step illustrations ship bundled inside the APK, so the app works
in airplane mode (FR-07 / NFR-04). Language switching is handled with a
`BaseActivity` that wraps `attachBaseContext` to apply the saved locale
before the layout is inflated — that way Kannada renders at the 20sp+ size
defined in `values-kn/dimens.xml`.

---

## Tech stack

- **Kotlin** 1.9.22
- **Android Gradle Plugin** 8.2.2 / Gradle 8.5
- **Min SDK** 24 (Android 7.0) — covers >99% of installed devices
- **Target SDK** 34
- **Material 3** components for buttons, cards, inputs
- **ViewBinding** enabled — no `findViewById` boilerplate

No external API keys required. No Firebase setup needed for the demo (the
PRD permits bundled read-only data; Firebase Realtime DB is wired in
`build.gradle` only when you want syncable updates).

---

## Building a release APK (for GitHub submission)

```bash
./gradlew assembleRelease
```

The unsigned APK lands in `app/build/outputs/apk/release/`. For your VTU
submission, debug APK from Android Studio's **Build → Build APK(s)** is
sufficient — drop it into the repo `releases/` folder and link from the
GitHub README.

---

## Known acceptable limitations (mention if asked)

- Illustrations are **vector drawables**, not photographs. Real platform
  photos can be dropped into `res/drawable/` and referenced from
  `StepData.kt` without code changes.
- The Metro graph includes 19 Purple + 21 Green + 1 shared Majestic stations
  (top-40 stops). Adding more stations is just appending to the list in
  `MetroNetwork.kt` — the BFS algorithm scales unchanged.
- Live train arrival is **out of scope** per the PRD.

---

## Credits

Built by MindMatrix VTU Interns for Project #73.
