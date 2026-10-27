package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.data.model.City
import au.edu.jcu.assessment.utilityapp.data.model.TemperatureUnit
import au.edu.jcu.assessment.utilityapp.data.model.UserPreferences
import au.edu.jcu.assessment.utilityapp.data.model.WindSpeedUnit
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    preferences: UserPreferences,
    onSelectCity: (City) -> Unit,
    onSetTemperatureUnit: (TemperatureUnit) -> Unit,
    onSetWindSpeedUnit: (WindSpeedUnit) -> Unit,
    onToggleOutdoorAdvice: (Boolean) -> Unit,
    onToggleApparentTemp: (Boolean) -> Unit,
    onToggleHumidity: (Boolean) -> Unit,
    onToggleWindSpeed: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = "App Settings & Preferences",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        // Location Section
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
            ) {
                Text(
                    text = "Location / City",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Select your target campus or city",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    City.PRESET_CITIES.forEach { city ->
                        FilterChip(
                            selected = preferences.selectedCity.id == city.id,
                            onClick = { onSelectCity(city) },
                            label = { Text(city.name) },
                        )
                    }
                }
            }
        }

        // Units Section
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Display Units",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )

                // Temperature Unit
                Column {
                    Text(
                        text = "Temperature Unit",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        TemperatureUnit.entries.forEachIndexed { index, unit ->
                            SegmentedButton(
                                selected = preferences.temperatureUnit == unit,
                                onClick = { onSetTemperatureUnit(unit) },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = TemperatureUnit.entries.size,
                                ),
                            ) {
                                Text("${unit.displayName} (${unit.symbol})")
                            }
                        }
                    }
                }

                HorizontalDivider()

                // Wind Speed Unit
                Column {
                    Text(
                        text = "Wind Speed Unit",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        WindSpeedUnit.entries.forEachIndexed { index, unit ->
                            SegmentedButton(
                                selected = preferences.windSpeedUnit == unit,
                                onClick = { onSetWindSpeedUnit(unit) },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = WindSpeedUnit.entries.size,
                                ),
                            ) {
                                Text("${unit.displayName} (${unit.symbol})")
                            }
                        }
                    }
                }
            }
        }

        // Feature Toggles Section
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
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "Display Cards",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )

                ToggleRow(
                    label = "Show Outdoor Advice Card",
                    checked = preferences.showOutdoorAdvice,
                    onCheckedChange = onToggleOutdoorAdvice,
                )

                ToggleRow(
                    label = "Show 'Feels Like' Temperature",
                    checked = preferences.showApparentTemp,
                    onCheckedChange = onToggleApparentTemp,
                )

                ToggleRow(
                    label = "Show Relative Humidity",
                    checked = preferences.showHumidity,
                    onCheckedChange = onToggleHumidity,
                )

                ToggleRow(
                    label = "Show Wind Speed",
                    checked = preferences.showWindSpeed,
                    onCheckedChange = onToggleWindSpeed,
                )
            }
        }
    }
}

@Composable
private fun ToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
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
