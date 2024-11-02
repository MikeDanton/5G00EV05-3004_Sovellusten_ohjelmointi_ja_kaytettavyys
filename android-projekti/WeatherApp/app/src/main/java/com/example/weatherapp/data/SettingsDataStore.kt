package com.example.weatherapp.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property to create a DataStore instance
private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(context: Context) {

    private val dataStore = context.dataStore

    // Preference keys
    companion object {
        val IS_CELSIUS = booleanPreferencesKey("is_celsius")
        val SHOW_TEMPERATURE = booleanPreferencesKey("show_temperature")
        val SHOW_WIND_SPEED = booleanPreferencesKey("show_wind_speed")
        val SHOW_DESCRIPTION = booleanPreferencesKey("show_description")
        val SHOW_HUMIDITY = booleanPreferencesKey("show_humidity")
        val SHOW_PRESSURE = booleanPreferencesKey("show_pressure")
    }

    // Read settings
    val isCelsius: Flow<Boolean> = dataStore.data.map { it[IS_CELSIUS] ?: true }
    val showTemperature: Flow<Boolean> = dataStore.data.map { it[SHOW_TEMPERATURE] ?: true }
    val showWindSpeed: Flow<Boolean> = dataStore.data.map { it[SHOW_WIND_SPEED] ?: true }
    val showDescription: Flow<Boolean> = dataStore.data.map { it[SHOW_DESCRIPTION] ?: true }
    val showHumidity: Flow<Boolean> = dataStore.data.map { it[SHOW_HUMIDITY] ?: true }
    val showPressure: Flow<Boolean> = dataStore.data.map { it[SHOW_PRESSURE] ?: true }

    // Save settings
    suspend fun saveIsCelsius(value: Boolean) {
        dataStore.edit { it[IS_CELSIUS] = value }
    }

    suspend fun saveShowTemperature(value: Boolean) {
        dataStore.edit { it[SHOW_TEMPERATURE] = value }
    }

    suspend fun saveShowWindSpeed(value: Boolean) {
        dataStore.edit { it[SHOW_WIND_SPEED] = value }
    }

    suspend fun saveShowDescription(value: Boolean) {
        dataStore.edit { it[SHOW_DESCRIPTION] = value }
    }

    suspend fun saveShowHumidity(value: Boolean) {
        dataStore.edit { it[SHOW_HUMIDITY] = value }
    }

    suspend fun saveShowPressure(value: Boolean) {
        dataStore.edit { it[SHOW_PRESSURE] = value }
    }
}
