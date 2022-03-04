package com.vm.office.room_business.sources

import com.vm.office.room_business.dtos.Room
import retrofit2.http.GET

/**
 * Room Data Source
 *
 * Retrofit API Interface
 */
interface RoomsDataSource {

    /**
     * Non-Paginated API
     *
     * @return list of room in the system.
     */
    @GET("/rooms")
    suspend fun getRooms(): List<Room>
}