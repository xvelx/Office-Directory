package com.vm.office.room_ui.state.data

import android.os.Parcelable
import com.vm.office.room_business.dtos.Room
import kotlinx.parcelize.Parcelize

/**
 * UiData of Room List and Detail.
 */
@Parcelize
data class RoomUiData(
    val roomId: String,
    val isOccupied: Boolean,
    val maxOccupancy: Int
) : Parcelable {

    companion object {
        /**
         * Converts the Room DTO to the RoomUiData
         */
        private fun fromRoom(room: Room) = room.run {
            RoomUiData(roomId, isOccupied, maxOccupancy)
        }

        /**
         * Converts the list of Room DTO to the RoomUiData
         */
        fun fromRoomList(roomList: List<Room>) = roomList.map { fromRoom(it) }
    }
}