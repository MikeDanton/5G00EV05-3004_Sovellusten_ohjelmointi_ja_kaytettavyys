package com.example.weatherapp.network

import retrofit2.http.GET
import retrofit2.http.Query

// Data classes for forecast and additional information
data class WeatherResponse(
    val name: String,
    val weather: List<WeatherDescription>,
    val main: MainData,
    val wind: WindData
)


data class WeatherDescription(val description: String)
data class MainData(val temp: Double, val humidity: Int, val pressure: Int) // Add more fields if needed
data class WindData(val speed: Double)

data class ForecastResponse(
    val list: List<ForecastData>
)

data class ForecastData(
    val dt_txt: String, // Date/time of forecasted data
    val main: MainData,
    val weather: List<WeatherDescription>,
    val wind: WindData
)

data class AirQualityResponse(
    val list: List<AirQualityData>
)

data class AirQualityData(
    val main: AirQualityMain
)

data class AirQualityMain(val aqi: Int)

interface WeatherApiService {
    // Current weather
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    // 5-day/3-hour forecast
    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse

    // Air quality data
    @GET("data/2.5/air_pollution")
    suspend fun getAirQuality(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): AirQualityResponse
}
