package com.example.stationery.data

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar

@Entity(tableName = "stickies")
data class Sticky(
    @PrimaryKey(autoGenerate = true)
    val title: String = "",
    val startDate: String = LocalDate.now()
        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
    val endDate: String = LocalDate.now()
        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
    val field: Field? = null, // CHANGE THIS LATER to first field in database
    val type: STICKY_TYPE = STICKY_TYPE.RESEARCH,
    val timeCommitted: Float? = null,
    val interest: INTEREST? = null,
)

enum class STICKY_TYPE {
    RESEARCH, BENCHMARK, EXPERIENCE
}

enum class INTEREST {
    LOW, MEDIUM, HIGH
}
