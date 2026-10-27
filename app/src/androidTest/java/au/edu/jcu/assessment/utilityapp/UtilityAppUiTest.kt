package au.edu.jcu.assessment.utilityapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import au.edu.jcu.assessment.utilityapp.data.model.UserPreferences
import au.edu.jcu.assessment.utilityapp.data.model.WeatherData
import au.edu.jcu.assessment.utilityapp.ui.screens.SettingsScreen
import au.edu.jcu.assessment.utilityapp.ui.screens.UtilityScreen
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme
import au.edu.jcu.assessment.utilityapp.ui.viewmodel.WeatherUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UtilityAppUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUtilityScreenSuccessDisplaysAtAGlanceCards() {
        val weatherData = WeatherData(
            temperatureCelsius = 28.0,
            apparentTemperatureCelsius = 30.0,
            humidityPercent = 70,
            weatherCode = 0,
            windSpeedKmh = 12.0,
            lastUpdatedIso = "2026-10-27T10:00",
        )

        composeTestRule.setContent {
            MobileTech_UtilityAppTheme {
                UtilityScreen(
                    uiState = WeatherUiState.Success(weatherData, UserPreferences()),
                    onRefresh = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Townsville (JCU)").assertIsDisplayed()
        composeTestRule.onNodeWithText("28°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("Clear Sky").assertIsDisplayed()
        composeTestRule.onNodeWithText("Outdoor Recommendation").assertIsDisplayed()
        composeTestRule.onNodeWithText("Feels Like").assertIsDisplayed()
        composeTestRule.onNodeWithText("Humidity").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wind").assertIsDisplayed()
    }

    @Test
    fun testSettingsScreenDisplaysPreferences() {
        composeTestRule.setContent {
            MobileTech_UtilityAppTheme {
                SettingsScreen(
                    preferences = UserPreferences(),
                    onSelectCity = {},
                    onSetTemperatureUnit = {},
                    onSetWindSpeedUnit = {},
                    onToggleOutdoorAdvice = {},
                    onToggleApparentTemp = {},
                    onToggleHumidity = {},
                    onToggleWindSpeed = {},
                )
            }
        }

        composeTestRule.onNodeWithText("App Settings & Preferences").assertIsDisplayed()
        composeTestRule.onNodeWithText("Location / City").assertIsDisplayed()
        composeTestRule.onNodeWithText("Townsville (JCU)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Display Units").assertIsDisplayed()
    }
}
