package com.example.weatherapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.network.WeatherResponse

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onNavigateToSettings: () -> Unit,
    onFetchWeatherByLocation: () -> Unit, // Callback to fetch weather by location
    modifier: Modifier = Modifier
) {
    // Collect the saved location data as State
    val savedLatitude = viewModel.savedLatitude.collectAsState().value
    val savedLongitude = viewModel.savedLongitude.collectAsState().value
    val isLocationBased = viewModel.isLocationBased.collectAsState().value

    LaunchedEffect(Unit) {
        if (isLocationBased && savedLatitude != null && savedLongitude != null) {
            viewModel.fetchWeatherByCoordinates(savedLatitude, savedLongitude)
        } else {
            viewModel.fetchWeather()
        }
    }

    // Collect UI states from the ViewModel
    val weather = viewModel.weather.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    val showTemperature = viewModel.showTemperature.collectAsState().value
    val showWindSpeed = viewModel.showWindSpeed.collectAsState().value
    val showDescription = viewModel.showDescription.collectAsState().value
    val showHumidity = viewModel.showHumidity.collectAsState().value
    val showPressure = viewModel.showPressure.collectAsState().value

    // Layout for the Weather Screen
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Settings Icon Button
        IconButton(onClick = onNavigateToSettings) {
            Icon(Icons.Default.Settings, contentDescription = stringResource(id = R.string.settings))
        }

        // Button to fetch weather by current location
        Button(onClick = onFetchWeatherByLocation, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Get Weather by Location")
        }

        // Display different content based on loading/error states
        when {
            isLoading -> CircularProgressIndicator()  // Show loading indicator
            errorMessage != null -> {                 // Show error message and retry button
                Text(text = errorMessage, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.fetchWeather() }) {
                    Text(stringResource(id = R.string.retry))
                }
            }
            weather != null -> WeatherDisplay(        // Show weather data
                viewModel = viewModel,
                weather = weather,
                showTemperature = showTemperature,
                showWindSpeed = showWindSpeed,
                showDescription = showDescription,
                showHumidity = showHumidity,
                showPressure = showPressure
            )
        }
    }
}

@Composable
fun WeatherDisplay(
    viewModel: WeatherViewModel,
    weather: WeatherResponse,
    showTemperature: Boolean,
    showWindSpeed: Boolean,
    showDescription: Boolean,
    showHumidity: Boolean,
    showPressure: Boolean
) {
    // Card to display weather details
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display location name
            Text(
                text = weather.name,  // Location name from the API response
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Conditionally display each piece of weather information based on settings
            if (showDescription) {
                Text(
                    text = stringResource(id = R.string.description_label) + " ${weather.weather[0].description}",
                    fontSize = 20.sp
                )
            }
            if (showTemperature) {
                Text(
                    text = stringResource(id = R.string.temperature_label) + " ${viewModel.getTemperatureString(weather.main.temp)}",
                    fontSize = 20.sp
                )
            }
            if (showWindSpeed) {
                Text(
                    text = stringResource(id = R.string.wind_speed_label) + " ${weather.wind.speed} m/s",
                    fontSize = 20.sp
                )
            }
            if (showHumidity) {
                Text(
                    text = stringResource(id = R.string.humidity_label) + " ${weather.main.humidity}%",
                    fontSize = 20.sp
                )
            }
            if (showPressure) {
                Text(
                    text = stringResource(id = R.string.pressure_label) + " ${weather.main.pressure} hPa",
                    fontSize = 20.sp
                )
            }
        }
    }
}