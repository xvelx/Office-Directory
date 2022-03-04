package com.vm.office.room_ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vm.office.room_ui.databinding.ItemRoomBinding
import com.vm.office.room_ui.state.data.RoomUiData

/**
 * Room List Adapter that takes list of RoomUiData and configures
 * the list view with the help of its associated ViewHolder (RoomViewHolder)
 */
class RoomListAdapter(private val uiDataList: List<RoomUiData>) :
    RecyclerView.Adapter<RoomListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomListViewHolder {
        return RoomListViewHolder(
            ItemRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomListViewHolder, position: Int) {
        holder.bind(uiDataList[position])
    }

    override fun getItemCount(): Int = uiDataList.size
}

/**
 * View Holder that configures and manages the views that presents room data.
 */
class RoomListViewHolder(private val itemRoomBinding: ItemRoomBinding) :
    RecyclerView.ViewHolder(itemRoomBinding.root) {

    /**
     * Binds the RoomUiData with the view.
     */
    fun bind(roomUiData: RoomUiData) {
        itemRoomBinding.room = roomUiData
    }
}