package com.example.stationery.data

import android.content.Context

interface StationeryContainer {
    val stickiesRepository: OfflineStickiesRepository
    val fieldsRepository: OfflineFieldsRepository
}

class StationeryDataContainer(private val context: Context) : StationeryContainer {
    override val stickiesRepository: OfflineStickiesRepository by lazy {
        OfflineStickiesRepository(StickyDatabase.getDatabase(context).stickyDAO())
    }

    override val fieldsRepository: OfflineFieldsRepository by lazy {
        OfflineFieldsRepository(FieldDatabase.getDatabase(context).fieldDAO())
    }
}