package com.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import com.weatherapp.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // Call the main UI function
                WeatherAppUI()
            }
        }
    }
}

@Composable
fun WeatherAppUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title: "Sääsovellus"
        Text(
            text = "Sääsovellus",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weather icon (e.g., sunny)
        Image(
            painter = painterResource(id = R.drawable.sunny),  // Replace with your actual image
            contentDescription = "Sunny",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weather description text
        Text(
            text = "Tänään on aurinkoista!",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Temperature and wind speed text
        Text(
            text = "Lämpötila: 25°C",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tuulen nopeus: 10 m/s",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Refresh button with a refresh icon
        Button(
            onClick = { /* Refresh the weather data */ },
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppPreview() {
    WeatherAppTheme {
        WeatherAppUI()
    }
}
