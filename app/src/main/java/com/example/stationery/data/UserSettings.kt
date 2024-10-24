package com.example.stationery.data

import android.net.Uri
import com.example.stationery.R

data class UserSettings(
    var displayInsights: Boolean = true,
    var displaySummaries: Boolean = true,
    var username: String = "Sample User",
    var newUsername: String = "",
    var imageID: Int = R.drawable.default_profile_photo
)
