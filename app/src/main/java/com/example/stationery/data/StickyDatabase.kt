package com.example.stationery.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sticky::class], version = 1, exportSchema = false)
abstract class StickyDatabase: RoomDatabase() {
    abstract fun stickyDAO(): StickyDAO

    companion object {
        @Volatile
        private var Instance: StickyDatabase? = null

        fun getDatabase(context: Context): StickyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, StickyDatabase::class.java, "sticky_database")
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}