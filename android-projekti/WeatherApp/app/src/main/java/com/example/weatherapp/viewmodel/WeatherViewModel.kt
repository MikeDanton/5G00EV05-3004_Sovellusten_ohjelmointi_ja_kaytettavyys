package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val repository: WeatherRepository,
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

    private val _isCelsius = MutableStateFlow(true)
    val isCelsius: Boolean get() = _isCelsius.value

    private val _showTemperature = MutableStateFlow(true)
    val showTemperature: StateFlow<Boolean> = _showTemperature

    private val _showWindSpeed = MutableStateFlow(true)
    val showWindSpeed: StateFlow<Boolean> = _showWindSpeed

    private val _showDescription = MutableStateFlow(true)
    val showDescription: StateFlow<Boolean> = _showDescription

    private val _showHumidity = MutableStateFlow(true)
    val showHumidity: StateFlow<Boolean> = _showHumidity

    private val _showPressure = MutableStateFlow(true)
    val showPressure: StateFlow<Boolean> = _showPressure

    init {
        fetchWeather() // Optionally fetch on initialization
    }

    fun setTemperatureUnit(useCelsius: Boolean) {
        _isCelsius.value = useCelsius
    }

    fun getTemperatureString(temp: Double): String {
        return "${temp.toInt()}°${if (_isCelsius.value) "C" else "F"}"
    }

    fun toggleTemperatureVisibility() {
        _showTemperature.value = !_showTemperature.value
    }

    fun toggleWindSpeedVisibility() {
        _showWindSpeed.value = !_showWindSpeed.value
    }

    fun toggleDescriptionVisibility() {
        _showDescription.value = !_showDescription.value
    }

    fun toggleHumidityVisibility() {
        _showHumidity.value = !_showHumidity.value
    }

    fun togglePressureVisibility() {
        _showPressure.value = !_showPressure.value
    }

    fun fetchWeather(city: String = defaultCity) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            val units = if (_isCelsius.value) "metric" else "imperial"
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
