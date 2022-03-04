package com.vm.office.room_business.repositories

import com.vm.office.room_business.BaseTest
import com.vm.office.room_business.dtos.Room
import com.vm.office.room_business.sources.RoomsDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RoomsRepositoryTest : BaseTest() {

    @MockK
    lateinit var roomsDataSource: RoomsDataSource

    private lateinit var roomsRepository: RoomsRepository

    @Before
    override fun setUp() {
        super.setUp()
        roomsRepository = RoomsRepository(roomsDataSource, testDispatcher)
    }

    private fun fetchRooms(): List<Room>? {
        var roomList: List<Room>? = null
        testScope.launch {
            roomList = roomsRepository.fetchRooms()
        }
        testScope.runCurrent()
        return roomList
    }

    @Test
    fun fetchRooms_whenDataCached_doesNotCallTheDataSource() {
        roomsRepository.roomsList = listOf(
            mockk(),
            mockk()
        )
        coEvery { roomsDataSource.getRooms() } returns emptyList()

        val roomList = fetchRooms()

        Assertions.assertThat(roomList).hasSize(2)
        coVerify(exactly = 0) { roomsDataSource.getRooms() }
    }

    @Test
    fun fetchRooms_whenDataNotCached_doesCallTheDataSource() {
        coEvery { roomsDataSource.getRooms() } returns listOf(
            mockk(),
            mockk(),
            mockk()
        )

        val roomList = fetchRooms()

        Assertions.assertThat(roomList).hasSize(3)
        coVerify(exactly = 1) { roomsDataSource.getRooms() }
    }

    private fun searchRoom(query: String): List<Room>? {
        var roomList: List<Room>? = null
        testScope.launch {
            roomList = roomsRepository.searchRooms(query)
        }
        testScope.runCurrent()

        return roomList
    }

    @Test
    fun searchRoom_whenDataNotCached_returnsEmptyResult() {
        val roomList = searchRoom("")

        Assertions.assertThat(roomList).isNotNull()
        Assertions.assertThat(roomList).isEmpty()
    }

    @Test
    fun searchRoom_whenDataCachedAndSearchQueryIsEmpty_returnsAllResults() {
        roomsRepository.roomsList = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )

        val roomList = searchRoom("")

        Assertions.assertThat(roomList).hasSize(3)
    }

    @Test
    fun searchRoom_whenIdMatchesTheQuery_returnsMatchedElements() {
        roomsRepository.roomsList = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        every { roomsRepository.roomsList[0].roomId } returns "1"
        every { roomsRepository.roomsList[1].roomId } returns "100"
        every { roomsRepository.roomsList[2].roomId } returns "200"

        val roomList = searchRoom("1")

        Assertions.assertThat(roomList).hasSize(2)
    }

    @Test
    fun searchRoom_whenNothingMatchesTheQuery_returnsEmptyElements() {
        roomsRepository.roomsList = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        every { roomsRepository.roomsList[0].roomId } returns "1"
        every { roomsRepository.roomsList[1].roomId } returns "100"
        every { roomsRepository.roomsList[2].roomId } returns "200"


        val roomList = searchRoom("5")

        Assertions.assertThat(roomList).isEmpty()
    }
}