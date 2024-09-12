package com.example.stationery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.stationery.data.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserSettingsViewModel : ViewModel() {
    private val _userSettingsUIState = MutableStateFlow(UserSettings())
    var showEditUsernameDialog by mutableStateOf(false)
        private set


    val userSettingsUIState: StateFlow<UserSettings> = _userSettingsUIState.asStateFlow()

    fun updateUsername(newUsername: Int) {
        _userSettingsUIState.value = _userSettingsUIState.value.copy(usernameID = newUsername)
    }

    fun updateProfileImage(newImageId: Int) {
        _userSettingsUIState.value = _userSettingsUIState.value.copy(imageID = newImageId)
    }

    fun toggleInsightDisplay() {
        _userSettingsUIState.update { currentState ->
            currentState.copy(
                displayInsights = !currentState.displayInsights
            )
        }
    }

    fun toggleSummaryDisplay() {
        _userSettingsUIState.update { currentState ->
            currentState.copy(
                displaySummaries = !currentState.displaySummaries
            )
        }
    }

    fun onShowEditUsernameDialog() {
        showEditUsernameDialog = true
    }

    fun onDismissEditUsernameDialog() {
        showEditUsernameDialog = false
    }
}