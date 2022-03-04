package com.vm.office.room_business.repositories

import androidx.annotation.VisibleForTesting
import com.vm.core.concurrency.di.IoDispatcher
import com.vm.office.room_business.sources.RoomsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Abstracts the room data source and provides the use-able methods to
 * fetch data required.
 */
class RoomsRepository @Inject constructor(
    private val dataSource: RoomsDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val roomsMutex = Mutex()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var roomsList = emptyList<com.vm.office.room_business.dtos.Room>()

    /**
     * Fetches the employee list from the Room Data Source.
     *
     * @return list of Room
     */
    suspend fun fetchRooms() = withContext(dispatcher) {
        if (roomsList.isEmpty()) {
            roomsMutex.withLock {
                roomsList = dataSource.getRooms()
            }
        }

        return@withContext roomsMutex.withLock { roomsList }
    }

    /**
     * Searches the Room with given query string (room ID).
     *
     * @return matched list of Room
     */
    suspend fun searchRooms(id: String) = withContext(dispatcher) {
        return@withContext roomsMutex.withLock {
            roomsList.filter { room ->
                room.roomId.contains(id)
            }
        }
    }
}