package au.edu.jcu.assessment.utilityapp.data.model

enum class TemperatureUnit(val symbol: String, val displayName: String) {
    CELSIUS("°C", "Celsius"),
    FAHRENHEIT("°F", "Fahrenheit"),
}

enum class WindSpeedUnit(val symbol: String, val displayName: String) {
    KMH("km/h", "Kilometers per hour"),
    MPH("mph", "Miles per hour"),
}

data class UserPreferences(
    val selectedCity: City = City.DEFAULT_CITY,
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val windSpeedUnit: WindSpeedUnit = WindSpeedUnit.KMH,
    val showOutdoorAdvice: Boolean = true,
    val showHumidity: Boolean = true,
    val showWindSpeed: Boolean = true,
    val showApparentTemp: Boolean = true,
)
