package com.example.stationery

import androidx.lifecycle.ViewModel
import com.example.stationery.data.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserSettingsViewModel : ViewModel() {
    private val _userSettings = MutableStateFlow(UserSettings())
    val userSettings: StateFlow<UserSettings> = _userSettings.asStateFlow()

    fun updateUsername(newUsername: Int) {
        _userSettings.value = _userSettings.value.copy(usernameID = newUsername)
    }

    fun updateProfileImage(newImageId: Int) {
        _userSettings.value = _userSettings.value.copy(imageID = newImageId)
    }
}