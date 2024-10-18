package com.example.stationery.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Sticky::class], version = 1, exportSchema = false)
@TypeConverters(StickyConverters::class)
abstract class StickyDatabase: RoomDatabase() {
    abstract fun stickyDAO(): StickyDAO

    companion object {
        @Volatile
        private var Instance: StickyDatabase? = null

        fun getDatabase(context: Context): StickyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, StickyDatabase::class.java, "sticky_database")
                    .build().also {
                        Instance = it
                    }
            }
        }
    }
}