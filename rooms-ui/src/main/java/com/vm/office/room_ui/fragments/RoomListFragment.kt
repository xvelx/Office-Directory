package com.vm.office.room_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vm.office.common_ui.fragments.SearchFragment
import com.vm.office.common_ui.state.UiState
import com.vm.office.common_ui.utils.handleUiState
import com.vm.office.room_ui.R
import com.vm.office.room_ui.adapters.RoomListAdapter
import com.vm.office.room_ui.databinding.FragmentRoomListBinding
import com.vm.office.room_ui.state.data.RoomUiData
import com.vm.office.room_ui.vms.RoomsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Presents all the room list to the user.
 */
@AndroidEntryPoint
class RoomListFragment : SearchFragment<FragmentRoomListBinding>() {

    private val roomsViewModel: RoomsViewModel by activityViewModels()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRoomListBinding = FragmentRoomListBinding
        .inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRoomListView()
    }

    override fun onItemSearch(query: String) {
        roomsViewModel.searchRooms(query)
    }

    private fun configureRoomListView() {
        roomsViewModel.roomsLiveData.observe(viewLifecycleOwner) {
            updateUiState(it, viewBinding.roomListRecyclerView)
        }
        roomsViewModel.fetchRooms()
    }

    private val roomListLayoutManager: GridLayoutManager
        get() = GridLayoutManager(
            context,
            resources.getInteger(R.integer.roomListSpanCount),
            GridLayoutManager.VERTICAL,
            false
        )

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun updateUiState(
        uiState: UiState<List<RoomUiData>>,
        recyclerView: RecyclerView,
        layoutManager: GridLayoutManager = roomListLayoutManager
    ) {
        handleUiState(uiState) { roomList ->
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = RoomListAdapter(roomList)
        }
    }
}