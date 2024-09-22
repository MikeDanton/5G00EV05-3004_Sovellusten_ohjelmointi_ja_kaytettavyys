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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.weatherapp.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
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
            painter = painterResource(id = R.drawable.sunny),  // Ensure sunny.png is in res/drawable
            contentDescription = "Sunny",
            modifier = Modifier.size(120.dp)  // Slightly increased the size for better visibility
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

        Spacer(modifier = Modifier.height(32.dp))  // Added a bit more spacing before the button

        // Refresh button with a refresh icon
        Button(
            onClick = { /* No functionality */ },
            modifier = Modifier.size(56.dp),  // Adjust button size
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF00FF)   // Magenta background color
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.refresh),  // Use your custom refresh drawable
                contentDescription = "Refresh",
                modifier = Modifier.size(24.dp)  // Adjust icon size if needed
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
