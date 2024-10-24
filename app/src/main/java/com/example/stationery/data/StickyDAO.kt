package com.example.stationery.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StickyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sticky: Sticky)

    @Update
    suspend fun update(sticky: Sticky)

    @Delete
    fun delete(sticky: Sticky)

    //get all stickies
    @Query("SELECT * from stickies ORDER BY date ASC")
    fun getAllStickies(): Flow<List<Sticky>>

    @Query("SELECT * FROM stickies WHERE title LIKE '%' || :searchQuery || '%' ORDER BY date ASC") //search query is a string
    fun searchStickies(searchQuery: String): Flow<List<Sticky>>

    //TO ADD MORE
}