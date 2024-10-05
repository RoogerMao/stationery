package com.example.stationery.logic.model

import com.example.stationery.data.Field

data class FieldUIState(
    val field: Field = Field(),
    val isFieldValid: Boolean = false
)
