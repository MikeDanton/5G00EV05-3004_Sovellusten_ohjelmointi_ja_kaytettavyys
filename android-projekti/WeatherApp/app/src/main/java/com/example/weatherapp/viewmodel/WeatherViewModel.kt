package com.example.weatherapp.viewmodel

import android.app.Application
import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.data.SettingsDataStore
import com.example.weatherapp.network.WeatherResponse
import com.example.weatherapp.network.LocationHelper
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val settingsDataStore: SettingsDataStore,
    private val application: Application,
    private val defaultCity: String = "Tampere",
    private val locationHelper: LocationHelper
) : ViewModel() {

    companion object {
        const val API_KEY = "662e007620411dc3a8a9428bf1841dde"
    }

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> get() = _weather

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _isLocationBased = MutableStateFlow(false) // Track location-based requests
    val isLocationBased: StateFlow<Boolean> get() = _isLocationBased

    // Expose saved latitude and longitude from settingsDataStore
    val savedLatitude: StateFlow<Double?> = settingsDataStore.lastLatitude.stateIn(
        viewModelScope, SharingStarted.Lazily, null
    )
    val savedLongitude: StateFlow<Double?> = settingsDataStore.lastLongitude.stateIn(
        viewModelScope, SharingStarted.Lazily, null
    )

    // Load settings from DataStore
    val isCelsius: StateFlow<Boolean> = settingsDataStore.isCelsius.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showTemperature: StateFlow<Boolean> = settingsDataStore.showTemperature.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showWindSpeed: StateFlow<Boolean> = settingsDataStore.showWindSpeed.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showDescription: StateFlow<Boolean> = settingsDataStore.showDescription.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showHumidity: StateFlow<Boolean> = settingsDataStore.showHumidity.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showPressure: StateFlow<Boolean> = settingsDataStore.showPressure.stateIn(viewModelScope, SharingStarted.Lazily, true)

    init {
        viewModelScope.launch {
            val savedLatitude = settingsDataStore.lastLatitude.firstOrNull()
            val savedLongitude = settingsDataStore.lastLongitude.firstOrNull()

            if (savedLatitude != null && savedLongitude != null) {
                fetchWeatherByCoordinates(savedLatitude, savedLongitude)
            } else {
                fetchWeather(defaultCity)
            }
        }
    }

    fun fetchWeatherByCoordinates(latitude: Double, longitude: Double) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            val units = if (isCelsius.value) "metric" else "imperial"
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    repository.fetchWeatherByCoordinates(latitude, longitude, API_KEY, units)
                }
            }
            result.onSuccess { weatherResponse ->
                _weather.value = weatherResponse
                _isLocationBased.value = true // Set to true as this is a location-based fetch
            }.onFailure {
                _errorMessage.value = application.getString(R.string.error_message)
            }
            _isLoading.value = false
        }
    }

    // Method to get a temperature string with the correct unit
    fun getTemperatureString(temp: Double): String {
        return "${temp.toInt()}°${if (isCelsius.value) "C" else "F"}"
    }

    fun fetchWeather(city: String = defaultCity) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            val units = if (isCelsius.value) "metric" else "imperial"
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    repository.fetchWeather(city, API_KEY, units)
                }
            }
            result.onSuccess { weatherResponse ->
                _weather.value = weatherResponse
                _isLocationBased.value = false // Set to false as this is a city-based fetch
            }.onFailure {
                _errorMessage.value = application.getString(R.string.error_message)
            }
            _isLoading.value = false
        }
    }

    fun fetchWeatherByLocation() {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            val location: Location? = withContext(Dispatchers.IO) {
                locationHelper.getLastLocation()?.await()
            }

            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude

                // Save the location for future use
                settingsDataStore.saveLastLocation(latitude, longitude)

                val units = if (isCelsius.value) "metric" else "imperial"
                val result = runCatching {
                    withContext(Dispatchers.IO) {
                        repository.fetchWeatherByCoordinates(latitude, longitude, API_KEY, units)
                    }
                }
                result.onSuccess { weatherResponse ->
                    _weather.value = weatherResponse
                }.onFailure {
                    _errorMessage.value = application.getString(R.string.error_message)
                }
            } else {
                _errorMessage.value = application.getString(R.string.error_no_permission)
            }
            _isLoading.value = false
        }
    }

    fun openMapForLocation(latitude: Double, longitude: Double) {
        val geoUri = Uri.parse("geo:$latitude,$longitude?q=weather")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(application.packageManager) != null) {
            application.startActivity(mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    // Methods for settings
    fun setTemperatureUnit(useCelsius: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveIsCelsius(useCelsius)
        }
    }

    fun toggleTemperatureVisibility() {
        viewModelScope.launch {
            val currentState = showTemperature.first() // Get the current value
            settingsDataStore.saveShowTemperature(!currentState) // Toggle the value
        }
    }

    fun toggleWindSpeedVisibility() {
        viewModelScope.launch {
            val currentState = showWindSpeed.first()
            settingsDataStore.saveShowWindSpeed(!currentState)
        }
    }

    fun toggleDescriptionVisibility() {
        viewModelScope.launch {
            val currentState = showDescription.first()
            settingsDataStore.saveShowDescription(!currentState)
        }
    }

    fun toggleHumidityVisibility() {
        viewModelScope.launch {
            val currentState = showHumidity.first()
            settingsDataStore.saveShowHumidity(!currentState)
        }
    }

    fun togglePressureVisibility() {
        viewModelScope.launch {
            val currentState = showPressure.first()
            settingsDataStore.saveShowPressure(!currentState)
        }
    }
}
