package com.example.weatherapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.data.SettingsDataStore
import com.example.weatherapp.network.LocationHelper
import com.example.weatherapp.network.NetworkModule
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.ui.screen.SettingsScreen
import com.example.weatherapp.ui.screen.WeatherScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {

    private val weatherRepository by lazy { WeatherRepository(NetworkModule.weatherApiService) }
    private val settingsDataStore by lazy { SettingsDataStore(this) }
    private val locationHelper by lazy { LocationHelper(this) }

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(weatherRepository, settingsDataStore, application, locationHelper)
    }

    // Register the permissions launcher for requesting location permissions
    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                // Permission granted, fetch weather by location
                viewModel.fetchWeatherByLocation()
            } else {
                // Permission denied - show an error message
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show()
            }
        }

    // Register the launcher for selecting a location on the map
    private val mapLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Assume the map activity returns selected latitude and longitude as extras
                val selectedLatitude = result.data?.getDoubleExtra("selectedLatitude", 0.0)
                val selectedLongitude = result.data?.getDoubleExtra("selectedLongitude", 0.0)
                if (selectedLatitude != null && selectedLongitude != null) {
                    viewModel.saveSelectedLocation(selectedLatitude, selectedLongitude)
                } else {
                    Toast.makeText(this, "No location selected.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp(
                viewModel,
                ::checkLocationPermissionAndFetchWeather,
                ::openMapForLocationSelection
            )
        }
    }

    // Function to check and request location permissions
    private fun checkLocationPermissionAndFetchWeather() {
        if (locationHelper.hasLocationPermission()) {
            viewModel.fetchWeatherByLocation()
        } else {
            requestLocationPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    // Function to open the map for selecting a location
    private fun openMapForLocationSelection() {
        // Intent to open map, customized for the specific map activity you’re using
        val intent = Intent(Intent.ACTION_VIEW) // Replace with actual intent for map selection
        mapLauncher.launch(intent)
    }
}

@Composable
fun WeatherApp(
    viewModel: WeatherViewModel,
    checkLocationPermissionAndFetchWeather: () -> Unit,
    openMapForLocationSelection: () -> Unit
) {
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
                        onFetchWeatherByLocation = checkLocationPermissionAndFetchWeather,
                        onSelectLocation = openMapForLocationSelection
                    )
                }
                composable("settingsScreen") {
                    SettingsScreen(viewModel = viewModel)
                }
            }
        }
    }
}
