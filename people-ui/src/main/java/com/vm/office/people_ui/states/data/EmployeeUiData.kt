package com.vm.office.people_ui.states.data

import android.os.Parcelable
import com.vm.office.people_business.dtos.Employee
import kotlinx.parcelize.Parcelize

/**
 * UiData of Employee List and Detail.
 */
@Parcelize
data class EmployeeUiData(
    val employeeId: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val emailId: String,
    val jobTitle: String,
    val favoriteColor: String
) : Parcelable {

    companion object {
        /**
         * Converts the Employee DTO to the EmployeeUiData
         */
        private fun fromEmployee(employee: Employee) = employee.run {
            EmployeeUiData(
                employeeId,
                firstName,
                lastName,
                avatarUrl,
                emailId,
                jobTitle,
                favoriteColor
            )
        }

        /**
         * Converts the list of Employee DTO to the EmployeeUiData
         */
        fun fromEmployeeList(employeeList: List<Employee>) = employeeList.map { fromEmployee(it) }
    }
}