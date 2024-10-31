package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.NetworkModule
import com.example.weatherapp.network.WeatherApiService
import com.example.weatherapp.network.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherApiService: WeatherApiService,
    private val defaultCity: String
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isCelsius = MutableStateFlow(true)
    val isCelsius: Boolean get() = _isCelsius.value

    fun setTemperatureUnit(useCelsius: Boolean) {
        _isCelsius.value = useCelsius
    }

    fun getTemperatureString(tempKelvin: Double): String {
        return if (_isCelsius.value) {
            "${(tempKelvin - 273.15).toInt()}°C"
        } else {
            "${((tempKelvin - 273.15) * 9 / 5 + 32).toInt()}°F"
        }
    }

    fun fetchWeather(city: String) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherResponse = weatherApiService.getWeather(city, "your_api_key")
                _weather.value = weatherResponse
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load weather data. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun retryFetchWeather(city: String) {
        fetchWeather(city)
    }
}