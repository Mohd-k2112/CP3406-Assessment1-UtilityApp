package au.edu.jcu.assessment.utilityapp.data.api

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timezone") val timezone: String?,
    @SerializedName("current") val current: CurrentWeatherDto?,
    @SerializedName("hourly") val hourly: HourlyWeatherDto?,
)

data class CurrentWeatherDto(
    @SerializedName("time") val time: String?,
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("relative_humidity_2m") val humidity: Int,
    @SerializedName("apparent_temperature") val apparentTemperature: Double,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("wind_speed_10m") val windSpeed: Double,
    @SerializedName("surface_pressure") val pressure: Double?,
    @SerializedName("is_day") val isDay: Int?,
)

data class HourlyWeatherDto(
    @SerializedName("time") val times: List<String>?,
    @SerializedName("temperature_2m") val temperatures: List<Double>?,
    @SerializedName("weather_code") val weatherCodes: List<Int>?,
)
