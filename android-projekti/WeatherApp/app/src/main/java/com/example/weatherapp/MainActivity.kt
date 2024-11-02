package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.data.SettingsDataStore
import com.example.weatherapp.network.NetworkModule
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.ui.screen.WeatherScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.screen.SettingsScreen
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {

    private val weatherRepository by lazy { WeatherRepository(NetworkModule.weatherApiService) }
    private val settingsDataStore by lazy { SettingsDataStore(this) }

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(weatherRepository, settingsDataStore, application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp(viewModel)
        }
    }
}

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    WeatherAppTheme {
        val navController = rememberNavController()

        Scaffold { padding ->
            NavHost(
                navController = navController,
                startDestination = "weatherScreen"
            ) {
                composable("weatherScreen") {
                    WeatherScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(padding),
                        onNavigateToSettings = { navController.navigate("settingsScreen") },
                        city = "Tampere"
                    )
                }
                composable("settingsScreen") {
                    SettingsScreen(viewModel = viewModel)
                }
            }
        }
    }
}
