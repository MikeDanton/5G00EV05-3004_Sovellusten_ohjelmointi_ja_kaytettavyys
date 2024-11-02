package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.SettingsDataStore
import com.example.weatherapp.repository.WeatherRepository

class WeatherViewModelFactory(
    private val repository: WeatherRepository,
    private val settingsDataStore: SettingsDataStore,
    private val application: Application,
    private val defaultCity: String = "Tampere"
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository, settingsDataStore, application, defaultCity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
