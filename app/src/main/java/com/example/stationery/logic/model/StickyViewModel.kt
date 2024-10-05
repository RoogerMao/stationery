package com.example.stationery.logic.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.stationery.data.OfflineStickiesRepository
import com.example.stationery.data.Sticky
import okhttp3.internal.readFieldOrNull

class StickyViewModel(private val stickiesRepository: OfflineStickiesRepository): ViewModel() {
    var stickyUIState by mutableStateOf(StickyUIState())
        private set
    var showStickyEditScreen by mutableStateOf(false)
        private set

    fun updateStickyUIState(newSticky: Sticky) {
        stickyUIState = StickyUIState(
            sticky = Sticky(
                title =  newSticky.title,
                date = newSticky.date,
                field = newSticky.field,
                type = newSticky.type,
                timeCommitted = newSticky.timeCommitted,
                interest = newSticky.interest
            ),
            isStickyValid = validateSticky(newSticky)
        )
    }

    suspend fun saveSticky() {
        if(validateSticky(stickyUIState.sticky)) {
            stickiesRepository.insertSticky(stickyUIState.sticky)
        }
    }

    private fun validateSticky(newSticky: Sticky): Boolean {
        return (newSticky.title != "") && (newSticky.field != null)
    }

    fun onShowEditStickyDialog() {
        showStickyEditScreen = true
    }

    fun onDismissEditStickyDialog() {
        showStickyEditScreen = false
    }
}