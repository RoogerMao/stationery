package com.example.stationery.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.stationery.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// ui state for lazy column
data class StickiesUIState(val stickyList: List<Sticky> = listOf())

// the information we want to know for displaying the UI
data class StickyUIState(
    val stickyDetails: StickyDetails = StickyDetails(),
    val areStickyDetailsValid: Boolean = false
)


// data needed for UI
data class StickyDetails(
    val id: Long = 0,
    val title: String = "",
    val date: String = LocalDate.now()
        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
    val field: String = Sticky.defaultCareerList[0],
    val type: String = "Research",
    val typeIconID: Int = R.drawable.baseline_lightbulb_24,
    val timeCommitted: String = "0.0",
    val interest: String = INTEREST.MEDIUM.name,
    val interestIconID: Int = R.drawable.baseline_signal_cellular_alt_2_bar_24
)

// the information we store in the database
@Entity(tableName = "stickies")
@TypeConverters(StickyConverters::class)
data class Sticky(
    @PrimaryKey val id: Long = 0,
    val title: String = "",
    val date: String,
    val field: String,
    val type: STICKY_TYPE,
    val timeCommitted: Float,
    val interest: INTEREST,
) {
    companion object {
        val defaultCareerList: List<String> = listOf(
            "Food Products and Processing Systems", "Plant Systems", "Animal Systems",
            "Power, Structural, and Technical Systems", "Natural Resource Systems",
            "Environmental Service Systems", "Agribusiness Systems", "Design/Pre-Construction",
            "Construction", "Maintenance/Operations", "Audio and Video Technology and Film",
            "Printing Technology", "Visual Arts", "Performing Arts", "Journalism and Broadcasting",
            "Telecommunications", "General Management", "Business Information Management",
            "Human Resources Management", "Operations Management", "Administrative Support",
            "Administration and Administrative Support", "Professional Support Services",
            "Teaching and Training", "Securities and Investments", "Business Finance",
            "Banking Services", "Insurance", "Accounting", "Governance", "National Security",
            "Foreign Service", "Planning", "Revenue and Taxation", "Regulation",
            "Public Management and Administration", "Therapeutic Services", "Diagnostic Services",
            "Health Informatics", "Support Services", "Biotechnology Research and Development",
            "Restaurants and Food/Beverage Services", "Lodging", "Travel and Tourism",
            "Recreation, Amusements and Attractions", "Early Childhood Development and Services",
            "Counseling and Mental Health Services", "Family and Community Services",
            "Personal Care Services", "Consumer Services", "Network Systems",
            "Information Support and Service", "Web and Digital Communications",
            "Programming and Software Development", "Correction Services",
            "Emergency and Fire Management Services", "Security and Protective Services",
            "Law Enforcement Services", "Legal Services", "Production",
            "Manufacturing Production Process Development", "Maintenance, Installation, and Repair",
            "Quality Assurance", "Logistics and Inventory Control",
            "Health, Safety and Environmental Assurance", "Marketing Management",
            "Professional Sales", "Merchandising", "Marketing Communications",
            "Marketing Research", "Engineering and Technology", "Science and Mathematics",
            "Transportation Operations", "Logistics Planning and Management Services",
            "Warehousing and Distribution Center Operations",
            "Facility and Mobile Equipment Maintenance",
            "Transportation, Systems, Infrastructure, Planning, Management and Regulation",
            "Healthy, Safety and Environmental Management", "Sales and Services"
        )
    }
}

enum class STICKY_TYPE {
    RESEARCH, BENCHMARK, EXPERIENCE
}

@ProvidedTypeConverter
class StickyConverters {
    @TypeConverter
    fun fromStickyType(stickyType: STICKY_TYPE): String {
        return stickyType.name[0].uppercaseChar() + stickyType.name.substring(1).lowercase()
    }

    @TypeConverter
    fun toStickyType(stickyType: String): STICKY_TYPE {
        return STICKY_TYPE.valueOf(stickyType.uppercase())
    }

    @TypeConverter
    fun fromStickyInterest(stickyInterest: INTEREST): String {
        return stickyInterest.name[0].uppercaseChar() + stickyInterest.name.substring(1).lowercase()
    }

    @TypeConverter
    fun toStickyInterest(stickyInterest: String): INTEREST {
        return INTEREST.valueOf(stickyInterest.uppercase())
    }
}

enum class INTEREST {
    LOW, MEDIUM, HIGH
}

// extension functions to convert between sticky UI class and sticky data class
fun Sticky.toStickyDetails(): StickyDetails = StickyDetails(
    id = id,
    title = title,
    date = date,
    field = field,
    type = StickyConverters().fromStickyType(type),
    timeCommitted = timeCommitted.toString(),
    interest = StickyConverters().fromStickyInterest(interest)
)

fun Sticky.toStickyUIState(areStickyDetailsValid: Boolean): StickyUIState = StickyUIState(
    stickyDetails = this.toStickyDetails(),
    areStickyDetailsValid = areStickyDetailsValid
)

fun StickyDetails.toSticky(): Sticky = Sticky(
    id = id,
    title = title,
    date = date,
    field = field,
    type = StickyConverters().toStickyType(type),
    timeCommitted = timeCommitted.toFloatOrNull() ?: 0.0F,
    interest = StickyConverters().toStickyInterest(interest)
)

