package com.example.weatherapp.repository

import com.example.weatherapp.network.WeatherApiService
import com.example.weatherapp.network.WeatherResponse

class WeatherRepository(private val weatherApiService: WeatherApiService) {

    suspend fun fetchWeather(city: String, apiKey: String, units: String): WeatherResponse {
        return weatherApiService.getWeather(city, apiKey, units)
    }
}
