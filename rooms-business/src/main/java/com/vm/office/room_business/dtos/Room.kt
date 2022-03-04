package com.vm.office.room_business.dtos

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * DTO for fetching data from Room Data Source.
 */
data class Room(
    @SerializedName("createdAt") val createdDate: Date,
    @SerializedName("id") val roomId: String,
    @SerializedName("isOccupied") val isOccupied: Boolean,
    @SerializedName("maxOccupancy") val maxOccupancy: Int
)