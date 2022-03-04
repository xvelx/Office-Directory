package com.vm.office.room_ui.state.data

import com.vm.office.room_business.dtos.Room
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

class RoomUiDataTest {

    @Test
    fun fromRoomList_whenProvided_convertsToUiData() {
        val roomList = listOf<Room>(
            mockk(relaxed = true),
            mockk(relaxed = true)
        )

        val roomUiDataList = RoomUiData.fromRoomList(roomList)

        Assertions.assertThat(roomUiDataList).hasSize(roomList.size)
    }

    @Test
    fun fromRoomList_whenConvertedToUiData_hasRightMapping() {
        val roomList = listOf(
            Room(
                Date(),
                "1234",
                true,
                5339
            )
        )

        val roomUiDataList = RoomUiData.fromRoomList(roomList)

        Assertions.assertThat(roomUiDataList[0].roomId).isEqualTo(roomList[0].roomId)
        Assertions.assertThat(roomUiDataList[0].isOccupied).isEqualTo(roomList[0].isOccupied)
        Assertions.assertThat(roomUiDataList[0].maxOccupancy).isEqualTo(roomList[0].maxOccupancy)
    }
}