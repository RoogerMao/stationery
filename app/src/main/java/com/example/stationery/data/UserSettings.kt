package com.example.stationery.data

import com.example.stationery.R

data class UserSettings(
    var displayInsights: Boolean = true,
    var displaySummaries: Boolean = true,
    var usernameID: Int = R.string.default_username,
    var imageID: Int = R.drawable.default_profile_photo
)
