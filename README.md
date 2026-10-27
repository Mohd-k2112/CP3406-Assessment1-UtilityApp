# AuraWeather — At-a-Glance Weather & Outdoor Utility App

**CP3406 / CP5307 Assessment 1: Utility App**  
**James Cook University (JCU)**

---

## Overview

**AuraWeather** is a modern, responsive, "at-a-glance" utility application built for Android. Designed specifically for university students, staff, and outdoors enthusiasts, AuraWeather delivers real-time weather metrics alongside intelligent outdoor activity recommendations tailored for campus life (e.g., JCU Townsville and Cairns campuses).

The app leverages **Jetpack Compose** with **Material Design 3**, clean **MVVM architecture**, **StateFlow** state management, and real-time networking with **Retrofit** using the keyless, public **Open-Meteo Weather API**.

---

## Core Features

### 1. Main Utility Screen (`UtilityScreen`)
- **At-a-Glance Hero Card**: Displays current city name, campus description, real-time temperature, condition emoji, WMO weather condition text, and a manual refresh button.
- **Contextual Outdoor Recommendation Card**: Generates smart recommendations based on real-time temperature and weather code (e.g., UV sunscreen and hydration warnings for hot Townsville days, umbrella callouts for rain, or pleasant study-outside suggestions).
- **Detail Metrics Grid**: Shows "Feels Like" apparent temperature, relative humidity percentage, and wind speed in customizable units.
- **Graceful Lifecycle States**: Seamlessly handles `Loading` (animated progress indicator), `Success` (cards display), and `Error` (friendly retry card) states.

### 2. Settings & Preferences Screen (`SettingsScreen`)
- **Campus & City Location Selector**: Instant switching between preset cities including JCU Townsville, JCU Cairns, Brisbane, Sydney, Melbourne, JCU Singapore, Tokyo, London, and New York.
- **Unit Conversion Controls**: Toggle between Celsius (°C) and Fahrenheit (°F) for temperature, and Kilometers per hour (km/h) and Miles per hour (mph) for wind speed.
- **Customizable Display Toggles**: Toggle visibility for individual cards (Outdoor Advice, Feels Like, Humidity, Wind Speed) to tailor the at-a-glance layout.

---

## Architecture & Technical Stack

```
au.edu.jcu.assessment.utilityapp
├── data
│   ├── api
│   │   ├── OpenMeteoApiService.kt     // Retrofit interface & singleton instance
│   │   └── WeatherResponse.kt         // Gson DTO data classes
│   ├── model
│   │   ├── City.kt                    // Location models with lat/lon coordinates
│   │   ├── UserPreferences.kt        // User settings state
│   │   └── WeatherData.kt             // Domain model with formatting & advice logic
│   └── repository
│       └── WeatherRepository.kt       // Weather repository interface & implementation
├── ui
│   ├── screens
│   │   ├── UtilityScreen.kt           // At-a-glance UI components & cards
│   │   └── SettingsScreen.kt          // Preferences & location picker UI
│   ├── theme
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodel
│       ├── WeatherUiState.kt          // Sealed interface (Loading, Success, Error)
│       └── WeatherViewModel.kt        // ViewModel managing StateFlow
└── MainActivity.kt                    // Activity entry point & Scaffold navigation
```

### Key Technical Concepts Applied (Weeks 1–5):
- **Week 1 (Kotlin & Android Studio)**: Idiomatic Kotlin data classes, sealed interfaces, extension functions, coroutines.
- **Week 2 (Jetpack Compose)**: Declarative UI, state hoisting, Scaffold, Column, Row, FlowRow, ElevatedCard, FilterChip, SegmentedButton.
- **Week 3 (Material Design 3)**: M3 color scheme integration, typography, surface variants, dark/light theme support.
- **Week 4 (App Architecture)**: `ViewModel` with `StateFlow`, clean `Repository` pattern, reactive UI state emitting.
- **Week 5 (Web APIs & Retrofit)**: Asynchronous networking using Retrofit 2 and Gson converter with Open-Meteo REST API.

---

## How to Build & Run

1. **Clone or Download**: Open the project folder in **Android Studio (Ladybug or newer)**.
2. **Gradle Sync**: Allow Android Studio to sync Gradle dependencies automatically.
3. **Run Project**: Select an Android Emulator or connected physical device running **Android 8.0 (API Level 26) or higher**.
4. **Target JDK**: Built with Java 17 toolchain compatibility.

---

## Unit & UI Testing

Unit tests cover the ViewModel, Repository abstraction, unit conversions, and domain formatting:
```bash
./gradlew test
```

Instrumented Compose UI tests verify screen composition and preview rendering:
```bash
./gradlew connectedAndroidTest
```

---

## License & Credits
Developed for **CP3406 / CP5307 Assessment 1: Utility App** at **James Cook University**.
Weather data provided by [Open-Meteo](https://open-meteo.com/).
