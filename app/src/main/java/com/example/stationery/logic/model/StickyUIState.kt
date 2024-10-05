package com.example.stationery.logic.model

import com.example.stationery.data.Sticky

data class StickyUIState(
    val sticky: Sticky = Sticky(),
    val isStickyValid: Boolean = false
)

