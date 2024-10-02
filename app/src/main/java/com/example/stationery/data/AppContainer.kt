package com.example.stationery.data

import android.content.Context

class AppContainer(private val context: Context) {
    val fieldsRepository: FieldsRepository by lazy {
        OfflineFieldsRepository(FieldDatabase.getDatabase(context).fieldDAO())
    }

    val stickiesRepository: StickiesRepository by lazy {
        OfflineStickiesRepository(StickyDatabase.getDatabase(context).stickyDAO())
    }
}