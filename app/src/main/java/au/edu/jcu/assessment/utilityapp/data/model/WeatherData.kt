package au.edu.jcu.assessment.utilityapp.data.model

data class HourlyForecastItem(
    val timeLabel: String,
    val temperatureCelsius: Double,
    val weatherCode: Int,
) {
    fun formattedTemperature(unit: TemperatureUnit): String {
        val temp = when (unit) {
            TemperatureUnit.CELSIUS -> temperatureCelsius
            TemperatureUnit.FAHRENHEIT -> (temperatureCelsius * 9 / 5) + 32
        }
        return "${temp.toInt()}${unit.symbol}"
    }

    val icon: String
        get() = when (weatherCode) {
            0 -> "☀️"
            1, 2 -> "🌤️"
            3 -> "☁️"
            45, 48 -> "🌫️"
            51, 53, 55, 56, 57 -> "🌦️"
            61, 63, 65, 66, 67, 80, 81, 82 -> "🌧️"
            71, 73, 75, 77, 85, 86 -> "❄️"
            95, 96, 99 -> "⛈️"
            else -> "🌡️"
        }
}

data class WeatherData(
    val temperatureCelsius: Double,
    val apparentTemperatureCelsius: Double,
    val humidityPercent: Int,
    val weatherCode: Int,
    val windSpeedKmh: Double,
    val surfacePressureHpa: Double = 1013.2,
    val isDaytime: Boolean = true,
    val lastUpdatedIso: String,
    val hourlyForecast: List<HourlyForecastItem> = emptyList(),
) {
    fun formattedTemperature(unit: TemperatureUnit): String {
        val temp = when (unit) {
            TemperatureUnit.CELSIUS -> temperatureCelsius
            TemperatureUnit.FAHRENHEIT -> (temperatureCelsius * 9 / 5) + 32
        }
        return "${temp.toInt()}${unit.symbol}"
    }

    fun formattedApparentTemperature(unit: TemperatureUnit): String {
        val temp = when (unit) {
            TemperatureUnit.CELSIUS -> apparentTemperatureCelsius
            TemperatureUnit.FAHRENHEIT -> (apparentTemperatureCelsius * 9 / 5) + 32
        }
        return "${temp.toInt()}${unit.symbol}"
    }

    fun formattedWindSpeed(unit: WindSpeedUnit): String {
        val speed = when (unit) {
            WindSpeedUnit.KMH -> windSpeedKmh
            WindSpeedUnit.MPH -> windSpeedKmh * 0.621371
        }
        return "${speed.toInt()} ${unit.symbol}"
    }

    val weatherCondition: String
        get() = when (weatherCode) {
            0 -> "Clear Sky"
            1 -> "Mainly Clear"
            2 -> "Partly Cloudy"
            3 -> "Overcast"
            45, 48 -> "Foggy"
            51, 53, 55 -> "Light Drizzle"
            56, 57 -> "Freezing Drizzle"
            61, 63 -> "Moderate Rain"
            65 -> "Heavy Rain"
            66, 67 -> "Freezing Rain"
            71, 73, 75, 77 -> "Snow"
            80, 81, 82 -> "Rain Showers"
            85, 86 -> "Snow Showers"
            95, 96, 99 -> "Thunderstorm"
            else -> "Variable Weather"
        }

    val weatherIcon: String
        get() = when (weatherCode) {
            0 -> if (isDaytime) "☀️" else "🌙"
            1, 2 -> if (isDaytime) "🌤️" else "🌌"
            3 -> "☁️"
            45, 48 -> "🌫️"
            51, 53, 55, 56, 57 -> "🌦️"
            61, 63, 65, 66, 67, 80, 81, 82 -> "🌧️"
            71, 73, 75, 77, 85, 86 -> "❄️"
            95, 96, 99 -> "⛈️"
            else -> "🌡️"
        }

    val outdoorAdvice: String
        get() = when {
            weatherCode in listOf(95, 96, 99) -> "⛈️ Severe weather expected! Stay indoors and avoid outdoor activities."
            weatherCode in listOf(61, 63, 65, 66, 67, 80, 81, 82) -> "🌧️ Rainy conditions! Bring an umbrella if walking outdoors on campus."
            weatherCode in listOf(51, 53, 55, 56, 57) -> "🌦️ Light drizzle! Consider an umbrella or light rain coat."
            temperatureCelsius >= 32.0 -> "☀️ Hot conditions! Stay hydrated, wear sunscreen, and seek shade."
            temperatureCelsius in 20.0..31.9 -> "🌿 Fantastic outdoor weather! Great for studying outside or going for a walk."
            temperatureCelsius in 15.0..19.9 -> "🌤️ Pleasant and mild! A light cardigan or long sleeves might be cozy."
            temperatureCelsius < 15.0 -> "🧥 Cool weather! Wear a warm jacket or hoodie when heading outdoors."
            else -> "Enjoy your day!"
        }

    val uvIndexCategory: String
        get() = when {
            temperatureCelsius >= 30.0 -> "High (Sun protection required)"
            temperatureCelsius >= 24.0 -> "Moderate (Wear hat & sunscreen)"
            else -> "Low (Comfortable outdoors)"
        }

    val comfortIndex: String
        get() = when {
            humidityPercent > 75 && temperatureCelsius > 28.0 -> "Humid & Tropical"
            humidityPercent > 60 -> "Moderately Humid"
            temperatureCelsius in 20.0..28.0 -> "Ideal Comfort"
            else -> "Fresh & Mild"
        }
}
