package com.vm.office.people_business.dtos

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * DTO for fetching data from Employee Data Source.
 */
data class Employee(
    @SerializedName("createdAt") val createdDate: Date,
    @SerializedName("id") val employeeId: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("avatar") val avatarUrl: String,
    @SerializedName("email") val emailId: String,
    @SerializedName("jobtitle") val jobTitle: String,
    @SerializedName("favouriteColor") val favoriteColor: String
)