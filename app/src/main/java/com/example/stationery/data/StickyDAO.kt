package com.example.stationery.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface StickyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sticky: Sticky)

    @Update
    suspend fun update(sticky: Sticky)

    @Delete
    suspend fun delete(sticky: Sticky)
}