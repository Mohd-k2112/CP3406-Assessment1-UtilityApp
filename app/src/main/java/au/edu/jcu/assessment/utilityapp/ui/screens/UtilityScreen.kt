package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.edu.jcu.assessment.utilityapp.data.model.UserPreferences
import au.edu.jcu.assessment.utilityapp.data.model.WeatherData
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme
import au.edu.jcu.assessment.utilityapp.ui.viewmodel.WeatherUiState

@Composable
fun UtilityScreen(
    uiState: WeatherUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        when (uiState) {
            is WeatherUiState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Fetching live weather...",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            is WeatherUiState.Error -> {
                ErrorCard(
                    message = uiState.message,
                    onRetry = onRefresh,
                )
            }

            is WeatherUiState.Success -> {
                WeatherContent(
                    weatherData = uiState.weatherData,
                    preferences = uiState.preferences,
                    onRefresh = onRefresh,
                )
            }
        }
    }
}

@Composable
private fun WeatherContent(
    weatherData: WeatherData,
    preferences: UserPreferences,
    onRefresh: () -> Unit,
) {
    val city = preferences.selectedCity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        // Hero Header Card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = city.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Text(
                            text = "${city.country} • ${city.description}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                        )
                    }

                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Weather",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        text = weatherData.weatherIcon,
                        fontSize = 52.sp,
                    )

                    Column {
                        Text(
                            text = weatherData.formattedTemperature(preferences.temperatureUnit),
                            fontSize = 46.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Text(
                            text = weatherData.weatherCondition,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
            }
        }

        // Outdoor Activity Advice Card
        if (preferences.showOutdoorAdvice) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Outdoor Advice",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(top = 2.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Outdoor Recommendation",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = weatherData.outdoorAdvice,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                    }
                }
            }
        }

        // Details Row Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (preferences.showApparentTemp) {
                MetricCard(
                    title = "Feels Like",
                    value = weatherData.formattedApparentTemperature(preferences.temperatureUnit),
                    icon = "🌡️",
                    modifier = Modifier.weight(1f),
                )
            }

            if (preferences.showHumidity) {
                MetricCard(
                    title = "Humidity",
                    value = "${weatherData.humidityPercent}%",
                    icon = "💧",
                    modifier = Modifier.weight(1f),
                )
            }

            if (preferences.showWindSpeed) {
                MetricCard(
                    title = "Wind",
                    value = weatherData.formattedWindSpeed(preferences.windSpeedUnit),
                    icon = "💨",
                    modifier = Modifier.weight(1f),
                )
            }
        }

        // Atmosphere & Comfort Conditions Card (Fills screen nicely)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Atmosphere & Environment",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Sun & UV Index:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    )
                    Text(
                        text = weatherData.uvIndexCategory,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Comfort Level:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    )
                    Text(
                        text = weatherData.comfortIndex,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Last Updated:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    )
                    Text(
                        text = weatherData.lastUpdatedIso,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = icon, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ErrorCard(
    message: String,
    onRetry: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = "Unable to Load Weather",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityScreenSuccessPreview() {
    MobileTech_UtilityAppTheme {
        UtilityScreen(
            uiState = WeatherUiState.Success(
                weatherData = WeatherData(
                    temperatureCelsius = 28.5,
                    apparentTemperatureCelsius = 31.0,
                    humidityPercent = 75,
                    weatherCode = 1,
                    windSpeedKmh = 14.5,
                    lastUpdatedIso = "2026-10-27T10:00",
                ),
                preferences = UserPreferences(),
            ),
            onRefresh = {},
        )
    }
}
