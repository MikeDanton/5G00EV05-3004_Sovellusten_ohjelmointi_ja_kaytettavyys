package com.example.weatherapp.network

import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherResponse(
    val weather: List<WeatherDescription>,
    val main: MainData,
    val wind: WindData
)

data class WeatherDescription(val description: String)
data class MainData(val temp: Double)
data class WindData(val speed: Double)

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}
