package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.WeatherApiService
import com.example.weatherapp.network.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherApiService: WeatherApiService,
    private val defaultCity: String = "Tampere"
) : ViewModel() {

    companion object {
        const val API_KEY = "662e007620411dc3a8a9428bf1841dde"
    }

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isCelsius = MutableStateFlow(true)
    val isCelsius: Boolean get() = _isCelsius.value

    // Set temperature unit preference
    fun setTemperatureUnit(useCelsius: Boolean) {
        _isCelsius.value = useCelsius
    }

    fun getTemperatureString(temp: Double): String {
        return "${temp.toInt()}°${if (_isCelsius.value) "C" else "F"}"
    }

    fun fetchWeather(city: String = defaultCity) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Use "metric" for Celsius and "imperial" for Fahrenheit
                val units = if (_isCelsius.value) "metric" else "imperial"
                val weatherResponse = weatherApiService.getWeather(city, API_KEY, units)
                _weather.value = weatherResponse
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load weather data. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Retry fetching weather data
    fun retryFetchWeather(city: String = defaultCity) {
        fetchWeather(city)
    }
}
