package com.example.stationery.data

import kotlinx.coroutines.flow.Flow

class OfflineFieldsRepository(private val fieldDAO: FieldDAO) : FieldsRepository {
    override fun getAllFieldsStream(): Flow<List<Field>> = fieldDAO.getAllFields()

    override suspend fun insertField(field: Field) = fieldDAO.insert(field)

    override suspend fun deleteField(field: Field) = fieldDAO.update(field)

    override suspend fun updateField(field: Field) = fieldDAO.delete(field)
}

interface FieldsRepository {
    //for now, leaving this for when we show all fields
    fun getAllFieldsStream(): Flow<List<Field>>

    suspend fun insertField(field: Field)

    suspend fun deleteField(field: Field)

    suspend fun updateField(field: Field)
}