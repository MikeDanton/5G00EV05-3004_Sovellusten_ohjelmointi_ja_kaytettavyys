package com.example.weatherapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.WeatherViewModel

@Composable
fun SettingsScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    val isCelsius = remember { mutableStateOf(viewModel.isCelsius) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Settings", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Temperature Unit: ")
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = {
                isCelsius.value = !isCelsius.value
                viewModel.setTemperatureUnit(isCelsius.value)
            }) {
                Text(if (isCelsius.value) "Celsius" else "Fahrenheit")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}