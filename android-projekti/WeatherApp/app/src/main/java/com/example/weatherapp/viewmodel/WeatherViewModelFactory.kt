package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.network.WeatherApiService

class WeatherViewModelFactory(
    private val weatherApiService: WeatherApiService,
    private val defaultCity: String = "Tampere"
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {  // Remove `?` in <T : ViewModel?>
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherApiService, defaultCity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
