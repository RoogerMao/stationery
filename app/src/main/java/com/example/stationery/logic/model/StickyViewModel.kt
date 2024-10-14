package com.example.stationery.logic.model

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stationery.StationeryApplication
import com.example.stationery.data.INTEREST
import com.example.stationery.data.OfflineStickiesRepository
import com.example.stationery.data.STICKY_TYPE
import com.example.stationery.data.StickiesRepository
import com.example.stationery.data.Sticky
import com.example.stationery.data.StickyDAO
import com.example.stationery.data.StickyDatabase
import com.example.stationery.data.StickyDetails
import com.example.stationery.data.StickyUIState
import com.example.stationery.data.stickyConverters
import com.example.stationery.data.toSticky
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StickyViewModel(
    private val stickyRepository: StickiesRepository
): ViewModel() {
    val _stickyUIState = MutableStateFlow(StickyUIState())
    var showStickyEditScreen by mutableStateOf(false)
        private set

    // non-mutable one we expose to the UI
    val stickyUIState: StateFlow<StickyUIState> = _stickyUIState.asStateFlow()

    // update a sticky's details
    fun updateSticky(stickyDetails: StickyDetails) {
        _stickyUIState.update { currentState ->
            currentState.copy(
                stickyDetails = stickyDetails,
                areStickyDetailsValid = validateStickyDetails(stickyDetails)
            )
        }
    }

    suspend fun saveSticky() {
        if(validateStickyDetails()) {
            stickyRepository.insertSticky(stickyUIState.value.stickyDetails.toSticky())
        }
    }

    private fun validateStickyDetails(stickyDetails: StickyDetails = stickyUIState.value.stickyDetails): Boolean {
        return (stickyDetails.title != "")
    }

    fun onShowEditStickyDialog() {
        showStickyEditScreen = true
    }

    fun onDismissEditStickyDialog() {
        showStickyEditScreen = false
    }

    fun onConfirmEditStickyDialog() {
        //TO DO

        showStickyEditScreen = false
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                StickyViewModel(stationeryApplication().container.stickiesRepository)
            }
        }
    }
}

fun CreationExtras.stationeryApplication(): StationeryApplication = (this[APPLICATION_KEY] as StationeryApplication)