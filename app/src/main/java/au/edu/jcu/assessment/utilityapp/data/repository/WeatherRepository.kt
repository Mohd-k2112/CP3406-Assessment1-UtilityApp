package au.edu.jcu.assessment.utilityapp.data.repository

import au.edu.jcu.assessment.utilityapp.data.api.OpenMeteoApiService
import au.edu.jcu.assessment.utilityapp.data.model.City
import au.edu.jcu.assessment.utilityapp.data.model.HourlyForecastItem
import au.edu.jcu.assessment.utilityapp.data.model.WeatherData

interface WeatherRepository {
    suspend fun fetchWeatherForCity(city: City): Result<WeatherData>
}

class WeatherRepositoryImpl(
    private val apiService: OpenMeteoApiService = OpenMeteoApiService.instance,
) : WeatherRepository {

    override suspend fun fetchWeatherForCity(city: City): Result<WeatherData> {
        return try {
            val response = apiService.getWeatherForecast(
                latitude = city.latitude,
                longitude = city.longitude,
            )
            val current = response.current
                ?: return Result.failure(Exception("Weather data unavailable for ${city.name}"))

            val hourlyItems = mutableListOf<HourlyForecastItem>()
            val times = response.hourly?.times
            val temps = response.hourly?.temperatures
            val codes = response.hourly?.weatherCodes
            if (times != null && temps != null && codes != null) {
                for (i in 0 until minOf(6, times.size, temps.size, codes.size)) {
                    val rawTime = times[i]
                    val hourLabel = if (rawTime.contains("T")) {
                        rawTime.substringAfter("T")
                    } else {
                        rawTime
                    }
                    hourlyItems.add(
                        HourlyForecastItem(
                            timeLabel = hourLabel,
                            temperatureCelsius = temps[i],
                            weatherCode = codes[i],
                        ),
                    )
                }
            }

            val weatherData = WeatherData(
                temperatureCelsius = current.temperature,
                apparentTemperatureCelsius = current.apparentTemperature,
                humidityPercent = current.humidity,
                weatherCode = current.weatherCode,
                windSpeedKmh = current.windSpeed,
                surfacePressureHpa = current.pressure ?: 1013.2,
                isDaytime = (current.isDay == 1),
                lastUpdatedIso = current.time ?: "Just now",
                hourlyForecast = hourlyItems,
            )
            Result.success(weatherData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
