package au.edu.jcu.assessment.utilityapp

import au.edu.jcu.assessment.utilityapp.data.model.City
import au.edu.jcu.assessment.utilityapp.data.model.TemperatureUnit
import au.edu.jcu.assessment.utilityapp.data.model.WeatherData
import au.edu.jcu.assessment.utilityapp.data.model.WindSpeedUnit
import au.edu.jcu.assessment.utilityapp.data.repository.WeatherRepository
import au.edu.jcu.assessment.utilityapp.ui.viewmodel.WeatherUiState
import au.edu.jcu.assessment.utilityapp.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository = object : WeatherRepository {
        var shouldReturnError = false
        var lastFetchedCity: City? = null

        override suspend fun fetchWeatherForCity(city: City): Result<WeatherData> {
            lastFetchedCity = city
            return if (shouldReturnError) {
                Result.failure(Exception("Network Error"))
            } else {
                Result.success(
                    WeatherData(
                        temperatureCelsius = 25.0,
                        apparentTemperatureCelsius = 27.0,
                        humidityPercent = 60,
                        weatherCode = 1,
                        windSpeedKmh = 10.0,
                        lastUpdatedIso = "2026-10-27T10:00",
                    ),
                )
            }
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoadWeatherSuccess() = runTest {
        val viewModel = WeatherViewModel(mockRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue("UI state should be Success", state is WeatherUiState.Success)
        if (state is WeatherUiState.Success) {
            assertEquals(25.0, state.weatherData.temperatureCelsius, 0.01)
            assertEquals("Townsville (JCU)", state.preferences.selectedCity.name)
        }
    }

    @Test
    fun testSelectCityUpdatesPreferencesAndFetchesData() = runTest {
        val viewModel = WeatherViewModel(mockRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        val cairns = City.PRESET_CITIES.first { it.id == "cairns" }
        viewModel.selectCity(cairns)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(cairns, mockRepository.lastFetchedCity)
        val state = viewModel.uiState.value
        assertTrue(state is WeatherUiState.Success)
        if (state is WeatherUiState.Success) {
            assertEquals("Cairns (JCU)", state.preferences.selectedCity.name)
        }
    }

    @Test
    fun testTemperatureUnitFormatting() {
        val weatherData = WeatherData(
            temperatureCelsius = 20.0,
            apparentTemperatureCelsius = 22.0,
            humidityPercent = 50,
            weatherCode = 0,
            windSpeedKmh = 10.0,
            lastUpdatedIso = "2026-10-27T10:00",
        )

        assertEquals("20°C", weatherData.formattedTemperature(TemperatureUnit.CELSIUS))
        assertEquals("68°F", weatherData.formattedTemperature(TemperatureUnit.FAHRENHEIT))
    }

    @Test
    fun testWindSpeedUnitFormatting() {
        val weatherData = WeatherData(
            temperatureCelsius = 20.0,
            apparentTemperatureCelsius = 22.0,
            humidityPercent = 50,
            weatherCode = 0,
            windSpeedKmh = 10.0,
            lastUpdatedIso = "2026-10-27T10:00",
        )

        assertEquals("10 km/h", weatherData.formattedWindSpeed(WindSpeedUnit.KMH))
        assertEquals("6 mph", weatherData.formattedWindSpeed(WindSpeedUnit.MPH))
    }
}
