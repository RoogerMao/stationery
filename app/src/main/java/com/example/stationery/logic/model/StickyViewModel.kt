package com.example.stationery.logic.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.stationery.data.INTEREST
import com.example.stationery.data.STICKY_TYPE
import com.example.stationery.data.StickiesRepository
import com.example.stationery.data.Sticky
import com.example.stationery.data.StickyDAO
import com.example.stationery.data.StickyDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StickyViewModel(
    private val stickyDatabase: StickyDatabase
): ViewModel() {
    private val _stickyUIState = MutableStateFlow(Sticky())
    var showStickyEditScreen by mutableStateOf(false)
        private set

    // non-mutable one we expose to the UI
    val stickyUIState: StateFlow<Sticky> = _stickyUIState.asStateFlow()

    fun updateSticky(
        //default values if not inputted
        title: String = _stickyUIState.value.title,
        date: String = _stickyUIState.value.date,
        field: String = _stickyUIState.value.field,
        type: STICKY_TYPE = _stickyUIState.value.type,
        timeCommitted: Float? = _stickyUIState.value.timeCommitted,
        interest: INTEREST? = _stickyUIState.value.interest
    ) {
        _stickyUIState.update { currentState ->
            currentState.copy(
                title = title,
                date = date,
                field = field,
                type = type,
                timeCommitted = timeCommitted,
                interest = interest
            )
        }
    }

    suspend fun saveSticky() {
        if(validateSticky(_stickyUIState.value)) {
            stickyDatabase.stickyDAO().insert(_stickyUIState.value)
        }
    }

    private fun validateSticky(newSticky: Sticky): Boolean {
        return (newSticky.title != "")
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
}