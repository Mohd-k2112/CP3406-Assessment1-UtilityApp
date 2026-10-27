package au.edu.jcu.assessment.utilityapp.ui.viewmodel

import au.edu.jcu.assessment.utilityapp.data.model.UserPreferences
import au.edu.jcu.assessment.utilityapp.data.model.WeatherData

sealed interface WeatherUiState {
    data object Loading : WeatherUiState

    data class Success(
        val weatherData: WeatherData,
        val preferences: UserPreferences,
    ) : WeatherUiState

    data class Error(
        val message: String,
        val preferences: UserPreferences,
    ) : WeatherUiState
}
