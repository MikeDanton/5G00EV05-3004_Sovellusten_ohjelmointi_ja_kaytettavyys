package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.SettingsDataStore
import com.example.weatherapp.network.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val settingsDataStore: SettingsDataStore,
    private val defaultCity: String = "Tampere"
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

    // Load settings from DataStore and initialize StateFlows
    val isCelsius: StateFlow<Boolean> = settingsDataStore.isCelsius.stateIn(
        viewModelScope, SharingStarted.Lazily, true
    )
    val showTemperature: StateFlow<Boolean> = settingsDataStore.showTemperature.stateIn(
        viewModelScope, SharingStarted.Lazily, true
    )
    val showWindSpeed: StateFlow<Boolean> = settingsDataStore.showWindSpeed.stateIn(
        viewModelScope, SharingStarted.Lazily, true
    )
    val showDescription: StateFlow<Boolean> = settingsDataStore.showDescription.stateIn(
        viewModelScope, SharingStarted.Lazily, true
    )
    val showHumidity: StateFlow<Boolean> = settingsDataStore.showHumidity.stateIn(
        viewModelScope, SharingStarted.Lazily, true
    )
    val showPressure: StateFlow<Boolean> = settingsDataStore.showPressure.stateIn(
        viewModelScope, SharingStarted.Lazily, true
    )

    init {
        fetchWeather() // Optionally fetch on initialization
    }

    // Update DataStore when temperature unit changes
    fun setTemperatureUnit(useCelsius: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveIsCelsius(useCelsius)
        }
    }

    fun getTemperatureString(temp: Double): String {
        return "${temp.toInt()}°${if (isCelsius.value) "C" else "F"}"
    }

    // Toggle visibility settings and save to DataStore
    fun toggleTemperatureVisibility() {
        viewModelScope.launch {
            settingsDataStore.saveShowTemperature(!showTemperature.value)
        }
    }

    fun toggleWindSpeedVisibility() {
        viewModelScope.launch {
            settingsDataStore.saveShowWindSpeed(!showWindSpeed.value)
        }
    }

    fun toggleDescriptionVisibility() {
        viewModelScope.launch {
            settingsDataStore.saveShowDescription(!showDescription.value)
        }
    }

    fun toggleHumidityVisibility() {
        viewModelScope.launch {
            settingsDataStore.saveShowHumidity(!showHumidity.value)
        }
    }

    fun togglePressureVisibility() {
        viewModelScope.launch {
            settingsDataStore.saveShowPressure(!showPressure.value)
        }
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
            }.onFailure { exception ->
                _errorMessage.value = "Failed to load weather data. Please try again."
            }
            _isLoading.value = false
        }
    }

    fun retryFetchWeather(city: String = defaultCity) {
        fetchWeather(city)
    }
}