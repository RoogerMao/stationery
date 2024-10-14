package com.example.stationery.data

import android.content.Context

interface StationeryContainer {
    val stickiesRepository: StickiesRepository
}

class StationeryDataContainer(private val context: Context) : StationeryContainer {
    override val stickiesRepository: StickiesRepository by lazy {
        OfflineStickiesRepository(StickyDatabase.getDatabase((context)).stickyDAO())
    }
}