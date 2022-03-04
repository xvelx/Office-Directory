package com.vm.office.room_ui.vms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vm.office.common_ui.state.UiState
import com.vm.office.common_ui.state.fromDataRequest
import com.vm.office.room_business.repositories.RoomsRepository
import com.vm.office.room_ui.state.data.RoomUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * State Holder of Room UI.
 *
 * Interacts with the Rooms Repository when necessary to get the data and publishes
 * the new required UiState.
 */
@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomsRepository: RoomsRepository
) : ViewModel() {
    private val mutableRoomsLiveData = MutableLiveData<UiState<List<RoomUiData>>>()
    val roomsLiveData: LiveData<UiState<List<RoomUiData>>> = mutableRoomsLiveData

    /**
     * Fetches the Room list.
     *
     * On completion - new UiState would be published that can be observed through
     * `roomsLiveData`
     */
    fun fetchRooms() {
        mutableRoomsLiveData.value = UiState.Loading
        viewModelScope.launch {
            val roomUiState = fromDataRequest({ roomsRepository.fetchRooms() }) {
                RoomUiData.fromRoomList(it)
            }
            mutableRoomsLiveData.postValue(roomUiState)
        }
    }

    /**
     * Searches the Room list.
     *
     * On completion - new UiState would be published that can be observed through
     * `roomsLiveData`
     */
    fun searchRooms(id: String) {
        mutableRoomsLiveData.value = UiState.Loading
        viewModelScope.launch {
            val roomUiState = fromDataRequest({ roomsRepository.searchRooms(id) }) {
                RoomUiData.fromRoomList(it)
            }
            mutableRoomsLiveData.postValue(roomUiState)
        }
    }
}