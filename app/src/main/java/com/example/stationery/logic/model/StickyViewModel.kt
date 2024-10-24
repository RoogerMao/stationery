package com.example.stationery.logic.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stationery.StationeryApplication
import com.example.stationery.data.StickiesRepository
import com.example.stationery.data.Sticky
import com.example.stationery.data.StickyDetails
import com.example.stationery.data.StickyUIState
import com.example.stationery.data.toSticky
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StickyViewModel(
    private val stickyRepository: StickiesRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    // for creating stickies
    private val _stickyUIState = MutableStateFlow(StickyUIState())
    private val _careerSearchText = MutableStateFlow("")
    private val _matchingCareers = MutableStateFlow(Sticky.defaultCareerList)

    var showStickyEditScreen by mutableStateOf(false)
        private set
    var showStickyDatePicker by mutableStateOf(false)
        private set
    var showInterestDropdown by mutableStateOf(false)
        private set
    var showTypeDropdown by mutableStateOf(false)
        private set

    // non-mutable values we expose to the UI
    val stickyUIState: StateFlow<StickyUIState> = _stickyUIState.asStateFlow()
    val careerSearchText: StateFlow<String> = _careerSearchText.asStateFlow()
    val matchingCareers: StateFlow<List<String>> = _matchingCareers.asStateFlow()

    fun updateMatchingCareers() {
        if(careerSearchText.value != "") {
            _matchingCareers.value = Sticky.defaultCareerList.filter {
                it.contains(careerSearchText.value, ignoreCase = true)
            }
        }
    }

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
            _stickyUIState.update { currentState -> currentState.copy(stickyDetails = StickyDetails()) }
        }
    }

    suspend fun deleteSticky() {
        stickyRepository.deleteSticky(stickyUIState.value.stickyDetails.toSticky())
    }

    private fun validateStickyDetails(stickyDetails: StickyDetails = stickyUIState.value.stickyDetails): Boolean {
        return (stickyDetails.title != "")
    }

    fun onCareerSearchTextChange(careerSearchQuery: String) {
        _careerSearchText.value = careerSearchQuery
    }

    fun onShowEditStickyDialog() {
        showStickyEditScreen = true
    }

    fun onDismissEditStickyDialog() {
        showStickyEditScreen = false
    }

    fun onToggleStickyDatePicker() {
        showStickyDatePicker = !showStickyDatePicker
    }

    fun onShowInterestDropdown() {
        showInterestDropdown = true
    }

    fun onDismissInterestDropdown() {
        showInterestDropdown = false
    }

    fun onShowTypeDropdown() {
        showTypeDropdown = true
    }

    fun onDismissTypeDropdown() {
        showTypeDropdown = false
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val stickyRepository = (this[APPLICATION_KEY] as StationeryApplication).container.stickiesRepository
                StickyViewModel(
                    stickyRepository = stickyRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}