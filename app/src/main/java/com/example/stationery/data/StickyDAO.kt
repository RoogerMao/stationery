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

    // filter by date
    @Query("SELECT * FROM stickies ORDER BY date DESC")
    fun getStickiesByDateDesc(): Flow<List<Sticky>>

    // filter by career field
    @Query("SELECT * FROM stickies WHERE field = :careerField ORDER BY date DESC")
    fun getStickiesByCareerField(careerField: String): Flow<List<Sticky>>

    // filter by activity type
    @Query("SELECT * FROM stickies WHERE type = :activityType ORDER BY date DESC")
    fun getStickiesByType(activityType: STICKY_TYPE): Flow<List<Sticky>>

    // filter by time committed
    @Query("SELECT * FROM stickies ORDER BY timeCommitted DESC")
    fun getStickiesByTimeDesc(): Flow<List<Sticky>>

    // filter by interest level
    @Query("SELECT * FROM stickies WHERE interest = :interestLevel ORDER BY date DESC")
    fun getStickiesByInterest(interestLevel: INTEREST): Flow<List<Sticky>>

    // multiple filters
    @Query("""
    SELECT * FROM stickies 
    WHERE (:careerField IS NULL OR field = :careerField)
    AND (:type IS NULL OR type = :type)
    AND (:interest IS NULL OR interest = :interest)
    ORDER BY 
    CASE 
        WHEN :sortBy = 'date' THEN date
        WHEN :sortBy = 'time' THEN timeCommitted
    END DESC
""")
    fun getStickiesWithFilters(
        careerField: String? = null,
        type: STICKY_TYPE? = null,
        interest: INTEREST? = null,
        sortBy: String = "date"
    ): Flow<List<Sticky>>

}