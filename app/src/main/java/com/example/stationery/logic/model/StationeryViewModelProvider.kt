package com.example.stationery.logic.model

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stationery.data.StationeryDataContainer

object StationeryViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            UserSettingsViewModel()
        }
    }
}