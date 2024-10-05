package com.example.stationery.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields")
data class Field(
    @PrimaryKey(autoGenerate = true)
    val field: String = ""
) {
    companion object {
        val defaultCareerList: MutableList<String> = mutableListOf(
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
