package com.example.weatherapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun SettingsScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    val isCelsius = viewModel.isCelsius.collectAsState().value
    val showTemperature = viewModel.showTemperature.collectAsState().value
    val showWindSpeed = viewModel.showWindSpeed.collectAsState().value
    val showDescription = viewModel.showDescription.collectAsState().value
    val showHumidity = viewModel.showHumidity.collectAsState().value
    val showPressure = viewModel.showPressure.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Temperature Unit Toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.temperature_unit))
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = {
                viewModel.setTemperatureUnit(!isCelsius)
            }) {
                Text(text = stringResource(id = if (isCelsius) R.string.celsius else R.string.fahrenheit))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show/Hide Data Toggles
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = showTemperature, onCheckedChange = { viewModel.toggleTemperatureVisibility() })
            Text(text = stringResource(id = R.string.show_temperature), modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = showWindSpeed, onCheckedChange = { viewModel.toggleWindSpeedVisibility() })
            Text(text = stringResource(id = R.string.show_wind_speed), modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = showDescription, onCheckedChange = { viewModel.toggleDescriptionVisibility() })
            Text(text = stringResource(id = R.string.show_description), modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = showHumidity, onCheckedChange = { viewModel.toggleHumidityVisibility() })
            Text(text = stringResource(id = R.string.show_humidity), modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = showPressure, onCheckedChange = { viewModel.togglePressureVisibility() })
            Text(text = stringResource(id = R.string.show_pressure), modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
