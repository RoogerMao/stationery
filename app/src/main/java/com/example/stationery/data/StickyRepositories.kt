package com.example.stationery.data

import kotlinx.coroutines.flow.Flow

class OfflineStickiesRepository(private val stickyDAO: StickyDAO) : StickiesRepository {
    override fun getAllStickiesStream(): Flow<List<Sticky>> = stickyDAO.getAllStickies()

    override suspend fun insertSticky(sticky: Sticky) = stickyDAO.insert(sticky)

    override suspend fun deleteSticky(sticky: Sticky) = stickyDAO.delete(sticky)

    override suspend fun updateSticky(sticky: Sticky) = stickyDAO.update(sticky)
}

interface StickiesRepository {
    fun getAllStickiesStream(): Flow<List<Sticky>>

    suspend fun insertSticky(sticky: Sticky)

    suspend fun deleteSticky(sticky: Sticky)

    suspend fun updateSticky(sticky: Sticky)
}

