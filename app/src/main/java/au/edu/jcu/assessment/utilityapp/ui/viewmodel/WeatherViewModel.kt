package au.edu.jcu.assessment.utilityapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import au.edu.jcu.assessment.utilityapp.data.model.City
import au.edu.jcu.assessment.utilityapp.data.model.TemperatureUnit
import au.edu.jcu.assessment.utilityapp.data.model.UserPreferences
import au.edu.jcu.assessment.utilityapp.data.model.WindSpeedUnit
import au.edu.jcu.assessment.utilityapp.data.repository.WeatherRepository
import au.edu.jcu.assessment.utilityapp.data.repository.WeatherRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepositoryImpl(),
) : ViewModel() {

    private val _preferences = MutableStateFlow(UserPreferences())
    val preferences: StateFlow<UserPreferences> = _preferences.asStateFlow()

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        loadWeather()
    }

    fun loadWeather() {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            val currentPrefs = _preferences.value
            repository.fetchWeatherForCity(currentPrefs.selectedCity)
                .onSuccess { weatherData ->
                    _uiState.value = WeatherUiState.Success(
                        weatherData = weatherData,
                        preferences = currentPrefs,
                    )
                }
                .onFailure { error ->
                    _uiState.value = WeatherUiState.Error(
                        message = error.localizedMessage ?: "Unable to fetch weather data. Check network connection.",
                        preferences = currentPrefs,
                    )
                }
        }
    }

    fun selectCity(city: City) {
        _preferences.value = _preferences.value.copy(selectedCity = city)
        loadWeather()
    }

    fun setTemperatureUnit(unit: TemperatureUnit) {
        _preferences.value = _preferences.value.copy(temperatureUnit = unit)
        updatePreferencesInUiState()
    }

    fun setWindSpeedUnit(unit: WindSpeedUnit) {
        _preferences.value = _preferences.value.copy(windSpeedUnit = unit)
        updatePreferencesInUiState()
    }

    fun toggleOutdoorAdvice(show: Boolean) {
        _preferences.value = _preferences.value.copy(showOutdoorAdvice = show)
        updatePreferencesInUiState()
    }

    fun toggleHumidity(show: Boolean) {
        _preferences.value = _preferences.value.copy(showHumidity = show)
        updatePreferencesInUiState()
    }

    fun toggleWindSpeed(show: Boolean) {
        _preferences.value = _preferences.value.copy(showWindSpeed = show)
        updatePreferencesInUiState()
    }

    fun toggleApparentTemp(show: Boolean) {
        _preferences.value = _preferences.value.copy(showApparentTemp = show)
        updatePreferencesInUiState()
    }

    private fun updatePreferencesInUiState() {
        val currentPrefs = _preferences.value
        when (val currentState = _uiState.value) {
            is WeatherUiState.Success -> {
                _uiState.value = currentState.copy(preferences = currentPrefs)
            }

            is WeatherUiState.Error -> {
                _uiState.value = currentState.copy(preferences = currentPrefs)
            }

            is WeatherUiState.Loading -> {
                // Keep loading
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel() as T
            }
        }
    }
}
