package au.edu.jcu.assessment.utilityapp.data.repository

import au.edu.jcu.assessment.utilityapp.data.api.OpenMeteoApiService
import au.edu.jcu.assessment.utilityapp.data.model.City
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

            val weatherData = WeatherData(
                temperatureCelsius = current.temperature,
                apparentTemperatureCelsius = current.apparentTemperature,
                humidityPercent = current.humidity,
                weatherCode = current.weatherCode,
                windSpeedKmh = current.windSpeed,
                lastUpdatedIso = current.time ?: "Just now",
            )
            Result.success(weatherData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
