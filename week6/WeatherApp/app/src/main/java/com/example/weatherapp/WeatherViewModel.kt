package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.NetworkModule
import com.example.weatherapp.network.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherResponse = NetworkModule.weatherApiService.getWeather(city, apiKey)
                _weather.value = weatherResponse
            } catch (e: Exception) {
                // Handle error, e.g., log or display error message
            }
        }
    }
}
