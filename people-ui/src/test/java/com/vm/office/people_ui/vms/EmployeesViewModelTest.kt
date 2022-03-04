package com.vm.office.people_ui.vms

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vm.office.common_ui.state.UiState
import com.vm.office.people_business.dtos.Employee
import com.vm.office.people_business.repositories.EmployeesRepository
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
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class EmployeesViewModelTest {

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()

    private val testScope = TestScope()

    @MockK(relaxed = true)
    lateinit var employeesRepository: EmployeesRepository

    @InjectMockKs
    lateinit var employeesViewModel: EmployeesViewModel

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
    fun fetchEmployees_whenSourceReturnsData_setsSuccessUiState() = testScope.runTest {
        val employeeListResponse: List<Employee> = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        coEvery { employeesRepository.fetchEmployees() } returns employeeListResponse

        launch {
            employeesViewModel.fetchEmployees()
        }
        runCurrent()

        assertThat(employeesViewModel.employeesLiveData.value)
            .isInstanceOf(UiState.Success::class.java)
        assertThat((employeesViewModel.employeesLiveData.value as UiState.Success).data)
            .hasSize(2)
    }

    @Test
    fun fetchEmployees_whenErrorOnAccessingSource_setsErrorUiState() = testScope.runTest {
        coEvery { employeesRepository.fetchEmployees() } throws IllegalStateException()

        launch {
            employeesViewModel.fetchEmployees()
        }
        runCurrent()

        assertThat(employeesViewModel.employeesLiveData.value)
            .isInstanceOf(UiState.Error::class.java)
        assertThat((employeesViewModel.employeesLiveData.value as UiState.Error).throwable)
            .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun searchEmployees_whenSourceReturnsData_setsSuccessUiState() = testScope.runTest {
        val employeeListResponse: List<Employee> = listOf(
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        coEvery { employeesRepository.searchEmployees("") } returns employeeListResponse

        launch {
            employeesViewModel.searchEmployees("")
        }
        runCurrent()

        assertThat(employeesViewModel.employeesLiveData.value)
            .isInstanceOf(UiState.Success::class.java)
        assertThat((employeesViewModel.employeesLiveData.value as UiState.Success).data)
            .hasSize(2)
    }

    @Test
    fun searchEmployees_whenErrorOnAccessingSource_setsErrorUiState() = testScope.runTest {
        coEvery { employeesRepository.searchEmployees("") } throws IllegalStateException()

        launch {
            employeesViewModel.searchEmployees("")
        }
        runCurrent()

        assertThat(employeesViewModel.employeesLiveData.value)
            .isInstanceOf(UiState.Error::class.java)
        assertThat((employeesViewModel.employeesLiveData.value as UiState.Error).throwable)
            .isInstanceOf(IllegalStateException::class.java)
    }
}