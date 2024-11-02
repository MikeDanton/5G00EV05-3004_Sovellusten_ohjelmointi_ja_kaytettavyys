package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.SettingsDataStore
import com.example.weatherapp.network.LocationHelper
import com.example.weatherapp.repository.WeatherRepository

class WeatherViewModelFactory(
    private val repository: WeatherRepository,
    private val settingsDataStore: SettingsDataStore,
    private val application: Application,
    private val locationHelper: LocationHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WeatherViewModel::class.java) -> {
                WeatherViewModel(repository, settingsDataStore, application, locationHelper) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
