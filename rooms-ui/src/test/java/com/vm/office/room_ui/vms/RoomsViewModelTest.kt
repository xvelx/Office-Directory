package com.vm.office.room_ui.vms

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vm.office.common_ui.state.UiState
import com.vm.office.room_business.dtos.Room
import com.vm.office.room_business.repositories.RoomsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class RoomsViewModelTest {

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()

    private val testScope = TestScope()

    @MockK(relaxed = true)
    lateinit var roomsRepository: RoomsRepository

    @InjectMockKs
    lateinit var roomsViewModel: RoomsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher(testScope.testScheduler))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchRooms_whenSourceReturnsData_setsSuccessUiState() = testScope.runTest {
        val roomListResponse: List<Room> = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        coEvery { roomsRepository.fetchRooms() } returns roomListResponse

        launch {
            roomsViewModel.fetchRooms()
        }
        runCurrent()

        Assertions.assertThat(roomsViewModel.roomsLiveData.value)
            .isInstanceOf(UiState.Success::class.java)
        Assertions.assertThat((roomsViewModel.roomsLiveData.value as UiState.Success).data)
            .hasSize(2)
    }

    @Test
    fun fetchRooms_whenErrorOnAccessingSource_setsErrorUiState() = testScope.runTest {
        coEvery { roomsRepository.fetchRooms() } throws IllegalStateException()

        launch {
            roomsViewModel.fetchRooms()
        }
        runCurrent()

        Assertions.assertThat(roomsViewModel.roomsLiveData.value)
            .isInstanceOf(UiState.Error::class.java)
        Assertions.assertThat((roomsViewModel.roomsLiveData.value as UiState.Error).throwable)
            .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun searchRooms_whenSourceReturnsData_setsSuccessUiState() = testScope.runTest {
        val roomListResponse: List<Room> = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        coEvery { roomsRepository.searchRooms("") } returns roomListResponse

        launch {
            roomsViewModel.searchRooms("")
        }
        runCurrent()

        Assertions.assertThat(roomsViewModel.roomsLiveData.value)
            .isInstanceOf(UiState.Success::class.java)
        Assertions.assertThat((roomsViewModel.roomsLiveData.value as UiState.Success).data)
            .hasSize(2)
    }

    @Test
    fun searchRooms_whenErrorOnAccessingSource_setsErrorUiState() = testScope.runTest {
        coEvery { roomsRepository.searchRooms("") } throws IllegalStateException()

        launch {
            roomsViewModel.searchRooms("")
        }
        runCurrent()

        Assertions.assertThat(roomsViewModel.roomsLiveData.value)
            .isInstanceOf(UiState.Error::class.java)
        Assertions.assertThat((roomsViewModel.roomsLiveData.value as UiState.Error).throwable)
            .isInstanceOf(IllegalStateException::class.java)
    }
}