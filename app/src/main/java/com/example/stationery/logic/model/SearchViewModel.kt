package com.example.stationery.logic.model

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

@SuppressLint("MutableCollectionMutableState")
class SearchViewModel(
    private val stickyRepository: StickiesRepository
): ViewModel() {
    // text search query
    private val _stickySearchQuery = MutableStateFlow("")

    // advanced search chip trackers
    var chipsSelected by mutableStateOf(mutableListOf(false, false, false, false, false))
        private set
    var chipsEnabled by mutableStateOf(mutableListOf(true, true, true, true, true))
        private set

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

    fun toggleSelectedChip(index: Int) {
        // restrict user to one filter
        chipsSelected[index] = !chipsSelected[index]

        if(chipsSelected[index]){
            // if this filter is selected, disable all other filters
            for(iteratingIndex in 0..4) if(iteratingIndex != index) chipsEnabled[iteratingIndex] = false
        } else {
            // if this filter is deselected, enable all other filters
            for(iteratingIndex in 0..4) if(iteratingIndex != index) chipsEnabled[iteratingIndex] = true
        }
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