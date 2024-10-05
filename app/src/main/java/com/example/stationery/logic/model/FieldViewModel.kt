package com.example.stationery.logic.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.stationery.data.Field
import com.example.stationery.data.OfflineFieldsRepository
import com.example.stationery.data.fieldToFieldUIState

class FieldViewModel(private val fieldsRepository: OfflineFieldsRepository) : ViewModel() {
    var fieldUIState by mutableStateOf(FieldUIState())
        private set

    fun updateFieldUIState(field: Field) {
        fieldUIState = field.fieldToFieldUIState(validateFieldInput(field))
    }

    // save a new field to the fields repository
    suspend fun saveField() {
        if (validateFieldInput(fieldUIState.field)) {
            fieldsRepository.insertField(
                Field(fieldUIState.field.fieldName)
            )
        }
    }

    private fun validateFieldInput(field: Field): Boolean {
        return field.fieldName != ""
    }
}