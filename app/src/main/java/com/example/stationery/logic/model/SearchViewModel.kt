package com.example.stationery.logic.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stationery.StationeryApplication
import com.example.stationery.data.StickiesRepository
import com.example.stationery.data.StickiesUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    private val stickyRepository: StickiesRepository
): ViewModel() {
    private val _stickySearchQuery = MutableStateFlow("")

    val stickySearchQuery = _stickySearchQuery.asStateFlow()

    val stickiesUIStateFlow: StateFlow<StickiesUIState> = _stickySearchQuery
        .debounce(300L) // delay to stop overflow
        .combine(stickyRepository.getAllStickiesStream()) { query, allStickies ->
            if (query.isEmpty()) {
                StickiesUIState(allStickies)
            } else {
                StickiesUIState(allStickies.filter {
                    it.title.contains(query, ignoreCase = true)
                })
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = StickiesUIState()
        )

    fun updateStickySearchQuery(query: String) { // update the search query
        _stickySearchQuery.value = query
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val stickyRepository = (this[APPLICATION_KEY] as StationeryApplication).container.stickiesRepository
                SearchViewModel(
                    stickyRepository = stickyRepository
                )
            }
        }
    }
}