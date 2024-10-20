package com.example.stationery.logic.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

    fun updateProfileImage(newImageId: Uri?) {
        _userSettingsUIState.value = _userSettingsUIState.value.copy(imageID = newImageId.hashCode())
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

    fun editNewUsername(editedNewUsername: String) {
        _userSettingsUIState.update { currentState ->
            currentState.copy(
                newUsername = editedNewUsername
            )
        }
    }

    fun onShowEditUsernameDialog() {
        showEditUsernameDialog = true
    }

    fun onDismissEditUsernameDialog() {
        showEditUsernameDialog = false
        _userSettingsUIState.update { currentState ->
            currentState.copy(
                newUsername = ""
            )
        }
    }

    fun onConfirmEditUsernameDialog(finalNewUsername: String) {
        showEditUsernameDialog = false
        _userSettingsUIState.update { currentState ->
            currentState.copy(
                username = finalNewUsername,
                newUsername = ""
            )
        }
    }
}