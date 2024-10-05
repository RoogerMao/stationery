package com.example.stationery.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Field::class], version = 1, exportSchema = false)
abstract class FieldDatabase : RoomDatabase() {
    abstract fun fieldDAO(): FieldDAO

    companion object {
        @Volatile
        //create a "static" instance of the database by using a companion object
        private var Instance: FieldDatabase? = null

        fun getDatabase(context: Context): FieldDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FieldDatabase::class.java, "field_database")
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }

    suspend fun loadDefaultCareers() {
        Field.defaultCareerList.sort()
        Field.defaultCareerList.forEach { defaultCareer ->
            Instance?.fieldDAO()?.insert(
                Field(defaultCareer)
            )
        }
    }
}