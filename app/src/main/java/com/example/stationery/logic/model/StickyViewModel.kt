package com.example.stationery.logic.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.stationery.data.OfflineStickiesRepository
import com.example.stationery.data.Sticky
import okhttp3.internal.readFieldOrNull

class StickyViewModel(private val stickiesRepository: OfflineStickiesRepository): ViewModel() {
    var stickyUISTate by mutableStateOf(StickyUIState())
        private set

    fun updateStickyUIState(newSticky: Sticky) {
        stickyUISTate = StickyUIState(
            sticky = Sticky(
                title =  newSticky.title,
                startDate = newSticky.startDate,
                endDate = newSticky.endDate,
                field = newSticky.field,
                type = newSticky.type,
                timeCommitted = newSticky.timeCommitted,
                interest = newSticky.interest
            ),
            isStickyValid = validateSticky(newSticky)
        )
    }

    suspend fun saveSticky() {
        if(validateSticky(stickyUISTate.sticky)) {
            stickiesRepository.insertSticky(stickyUISTate.sticky)
        }
    }

    private fun validateSticky(newSticky: Sticky): Boolean {
        return (newSticky.title != "") && (newSticky.endDate >= newSticky.startDate)
                && (newSticky.field != null)
    }
}