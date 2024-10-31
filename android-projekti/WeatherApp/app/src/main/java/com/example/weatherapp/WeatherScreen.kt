package com.example.weatherapp

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.network.WeatherResponse

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onNavigateToSettings: () -> Unit,
    city: String,
    modifier: Modifier = Modifier
) {
    // Launch fetchWeather only on first composition
    LaunchedEffect(Unit) {
        viewModel.fetchWeather(city)
    }

    val weather = viewModel.weather.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onNavigateToSettings) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }

        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> {
                Text(text = errorMessage, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.fetchWeather(city) }) {
                    Text("Retry")
                }
            }
            weather != null -> WeatherDisplay(viewModel, weather)
        }
    }
}

@Composable
fun WeatherDisplay(viewModel: WeatherViewModel, weather: WeatherResponse) {
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
            Text(text = "Description: ${weather.weather[0].description}", fontSize = 20.sp)
            Text(
                text = "Temperature: ${viewModel.getTemperatureString(weather.main.temp)}",
                fontSize = 20.sp
            )
            Text(text = "Wind Speed: ${weather.wind.speed} m/s", fontSize = 20.sp)
        }
    }
}
