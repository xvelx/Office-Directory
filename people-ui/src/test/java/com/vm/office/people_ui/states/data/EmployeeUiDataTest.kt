package com.vm.office.people_ui.states.data

import com.vm.office.people_business.dtos.Employee
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class EmployeeUiDataTest {

    @Test
    fun fromEmployeeList_whenProvided_convertsToUiData() {
        val employeeList = listOf<Employee>(
            mockk(relaxed = true),
            mockk(relaxed = true)
        )

        val employeeUiDataList = EmployeeUiData.fromEmployeeList(employeeList)

        assertThat(employeeUiDataList).hasSize(employeeList.size)
    }

    @Test
    fun fromEmployeeList_whenConvertedToUiData_hasRightMapping() {
        val employeeList = listOf(
            Employee(
                Date(),
                "1234",
                "Rob",
                "W",
                "http://mockurl",
                "mock@data.co",
                "Trainee",
                "#FFF"
            )
        )

        val employeeUiDataList = EmployeeUiData.fromEmployeeList(employeeList)

        assertThat(employeeList[0].employeeId).isEqualTo(employeeUiDataList[0].employeeId)
        assertThat(employeeList[0].firstName).isEqualTo(employeeUiDataList[0].firstName)
        assertThat(employeeList[0].lastName).isEqualTo(employeeUiDataList[0].lastName)
        assertThat(employeeList[0].avatarUrl).isEqualTo(employeeUiDataList[0].avatarUrl)
        assertThat(employeeList[0].jobTitle).isEqualTo(employeeUiDataList[0].jobTitle)
        assertThat(employeeList[0].favoriteColor).isEqualTo(employeeUiDataList[0].favoriteColor)
    }
}