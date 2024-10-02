package com.example.stationery.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "stickies")
data class Sticky(
    @PrimaryKey(autoGenerate = true)
    val title: String = "",
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val field: Field,
    val type: STICKY_TYPE,
    val timeCommitted: Float?,
    val interest: INTEREST?,
)

enum class STICKY_TYPE {
    RESEARCH, BENCHMARK, EXPERIENCE
}

enum class INTEREST {
    LOW, MEDIUM, HIGH
}
