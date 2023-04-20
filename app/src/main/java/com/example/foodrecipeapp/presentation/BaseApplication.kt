package com.example.foodrecipeapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {
//    should be saved in a datastore or cache
    val isDark = mutableStateOf(false)
    fun toggleLightTheme() {
        isDark.value = !isDark.value
    }
}