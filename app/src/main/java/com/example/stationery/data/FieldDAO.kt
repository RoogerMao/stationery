package com.example.stationery.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface FieldDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(field: Field)

    @Delete
    suspend fun delete(field: Field)

    @Update
    suspend fun update(field: Field)

    //get all fields
    @Query("SELECT * from fields ORDER BY field ASC")
    fun getAllFields(): Flow<List<Field>>
}