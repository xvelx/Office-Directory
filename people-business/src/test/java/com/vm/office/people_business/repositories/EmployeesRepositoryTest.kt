package com.vm.office.people_business.repositories

import com.vm.office.people_business.BaseTest
import com.vm.office.people_business.dtos.Employee
import com.vm.office.people_business.sources.EmployeesDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EmployeesRepositoryTest : BaseTest() {

    @MockK
    lateinit var employeeDataSource: EmployeesDataSource

    private lateinit var employeesRepository: EmployeesRepository

    @Before
    override fun setUp() {
        super.setUp()

        employeesRepository = EmployeesRepository(employeeDataSource, testDispatcher)
    }

    private fun fetchEmployees(): List<Employee>? {
        var employeeList: List<Employee>? = null
        testScope.launch {
            employeeList = employeesRepository.fetchEmployees()
        }
        testScope.runCurrent()
        return employeeList
    }

    @Test
    fun fetchEmployees_whenDataCached_doesNotCallTheDataSource() {
        employeesRepository.employeeList = listOf(
            mockk(),
            mockk()
        )
        coEvery { employeeDataSource.getEmployees() } returns emptyList()

        val employeeList = fetchEmployees()

        assertThat(employeeList).hasSize(2)
        coVerify(exactly = 0) { employeeDataSource.getEmployees() }
    }

    @Test
    fun fetchEmployees_whenDataNotCached_doesCallTheDataSource() {
        coEvery { employeeDataSource.getEmployees() } returns listOf(
            mockk(),
            mockk(),
            mockk()
        )

        val employeeList = fetchEmployees()

        assertThat(employeeList).hasSize(3)
        coVerify(exactly = 1) { employeeDataSource.getEmployees() }
    }

    private fun searchEmployee(query: String): List<Employee>? {
        var employeeList: List<Employee>? = null
        testScope.launch {
            employeeList = employeesRepository.searchEmployees(query)
        }
        testScope.runCurrent()

        return employeeList
    }

    @Test
    fun searchEmployees_whenDataNotCached_returnsEmptyResult() {
        val employeeList = searchEmployee("")

        assertThat(employeeList).isNotNull()
        assertThat(employeeList).isEmpty()
    }

    @Test
    fun searchEmployees_whenDataCachedAndSearchQueryIsEmpty_returnsAllResults() {
        employeesRepository.employeeList = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )

        val employeeList = searchEmployee("")

        assertThat(employeeList).hasSize(3)
    }

    @Test
    fun searchEmployees_whenFirstNameMatchesTheQuery_returnsMatchedElements() {
        employeesRepository.employeeList = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        every { employeesRepository.employeeList[0].firstName } returns "Hubble"
        every { employeesRepository.employeeList[1].firstName } returns "Humming"
        every { employeesRepository.employeeList[2].firstName } returns "Kepler"

        val employeeList = searchEmployee("Hu")

        assertThat(employeeList).hasSize(2)
    }

    @Test
    fun searchEmployees_whenLastNameMatchesTheQuery_returnsMatchedElements() {
        employeesRepository.employeeList = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        every { employeesRepository.employeeList[0].lastName } returns "Hubble"
        every { employeesRepository.employeeList[1].lastName } returns "Humming"
        every { employeesRepository.employeeList[2].lastName } returns "Kepler"

        val employeeList = searchEmployee("Hu")

        assertThat(employeeList).hasSize(2)
    }

    @Test
    fun searchEmployees_whenJobTitleMatchesTheQuery_returnsMatchedElements() {
        employeesRepository.employeeList = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        every { employeesRepository.employeeList[0].lastName } returns "Engineer"
        every { employeesRepository.employeeList[1].lastName } returns "Doctor"
        every { employeesRepository.employeeList[2].lastName } returns "Electrician"

        val employeeList = searchEmployee("En")

        assertThat(employeeList).hasSize(1)
    }

    @Test
    fun searchEmployees_whenNothingMatchesTheQuery_returnsEmptyElements() {
        employeesRepository.employeeList = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        every { employeesRepository.employeeList[0].firstName } returns "Hubble"
        every { employeesRepository.employeeList[1].firstName } returns "Kepler"
        every { employeesRepository.employeeList[2].firstName } returns "Zebra"
        every { employeesRepository.employeeList[0].lastName } returns "Telescope"
        every { employeesRepository.employeeList[1].lastName } returns "Program"
        every { employeesRepository.employeeList[2].lastName } returns "Electrician"
        every { employeesRepository.employeeList[0].jobTitle } returns "Engineer"
        every { employeesRepository.employeeList[1].jobTitle } returns "Doctor"
        every { employeesRepository.employeeList[2].jobTitle } returns "Electrician"

        val employeeList = searchEmployee("ZZ")

        assertThat(employeeList).isEmpty()
    }
}