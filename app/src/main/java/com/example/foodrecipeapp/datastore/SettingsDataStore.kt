package com.example.foodrecipeapp.datastore

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodrecipeapp.presentation.BaseApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDataStore

constructor(
    private val context: Context
)
{
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_key_theme")
    }

    val getDarkThemeMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_THEME_KEY] ?: false
    }

    suspend fun changeDarkThemeMode(toggle: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = toggle
        }
    }

}