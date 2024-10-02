package com.example.stationery.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface FieldDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(field: Field)

    @Delete
    suspend fun delete(field: Field)

    @Update
    suspend fun update(field: Field)
}