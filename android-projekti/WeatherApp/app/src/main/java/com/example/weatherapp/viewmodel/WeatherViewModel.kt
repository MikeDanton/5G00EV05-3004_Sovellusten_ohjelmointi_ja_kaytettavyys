package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
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
    private val application: Application,
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

    // Load settings from DataStore
    val isCelsius: StateFlow<Boolean> = settingsDataStore.isCelsius.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showTemperature: StateFlow<Boolean> = settingsDataStore.showTemperature.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showWindSpeed: StateFlow<Boolean> = settingsDataStore.showWindSpeed.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showDescription: StateFlow<Boolean> = settingsDataStore.showDescription.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showHumidity: StateFlow<Boolean> = settingsDataStore.showHumidity.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val showPressure: StateFlow<Boolean> = settingsDataStore.showPressure.stateIn(viewModelScope, SharingStarted.Lazily, true)

    init {
        fetchWeather()
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

    // Generalized toggle function
    private fun toggleBooleanPreference(flow: StateFlow<Boolean>, saveAction: suspend (Boolean) -> Unit) {
        viewModelScope.launch {
            saveAction(!flow.value)
        }
    }

    fun toggleTemperatureVisibility() = toggleBooleanPreference(showTemperature) { settingsDataStore.saveShowTemperature(it) }
    fun toggleWindSpeedVisibility() = toggleBooleanPreference(showWindSpeed) { settingsDataStore.saveShowWindSpeed(it) }
    fun toggleDescriptionVisibility() = toggleBooleanPreference(showDescription) { settingsDataStore.saveShowDescription(it) }
    fun toggleHumidityVisibility() = toggleBooleanPreference(showHumidity) { settingsDataStore.saveShowHumidity(it) }
    fun togglePressureVisibility() = toggleBooleanPreference(showPressure) { settingsDataStore.saveShowPressure(it) }

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
            }.onFailure {
                _errorMessage.value = application.getString(R.string.error_message)
            }
            _isLoading.value = false
        }
    }

    fun retryFetchWeather(city: String = defaultCity) {
        fetchWeather(city)
    }
}
