package com.example.stationery.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields")
data class Field(
    @PrimaryKey(autoGenerate = true)
    val field: String = ""
)
