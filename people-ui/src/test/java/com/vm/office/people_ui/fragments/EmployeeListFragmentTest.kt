package com.vm.office.people_ui.fragments

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.vm.office.common_ui.activities.ProgressActivity
import com.vm.office.common_ui.state.UiState
import com.vm.office.people_ui.adapters.EmployeesListAdapter
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EmployeeListFragmentTest {

    @Test
    fun employeeLiveData_onLoadingState_callsShowLoading() {
        val employeeListFragment: EmployeeListFragment = spyk()
        val activity = mockk<TestActivity>()

        every { activity.showProgressIndicator() } returns Unit
        every { employeeListFragment.activity } returns activity

        employeeListFragment.updateUiState(UiState.Loading, mockk(), mockk())

        verify(exactly = 1) { activity.showProgressIndicator() }
    }

    @Test
    fun employeeLiveData_onErrorState_callsShowError() {
        val employeeListFragment: EmployeeListFragment = spyk()
        val exceptionCaptor = slot<Throwable>()
        val activity = mockk<TestActivity>()

        every { activity.showError(capture(exceptionCaptor)) } returns Unit
        every { activity.hideProgressIndicator() } returns Unit
        every { employeeListFragment.activity } returns activity

        employeeListFragment.updateUiState(UiState.Error(IllegalStateException()), mockk(), mockk())

        verify(exactly = 1) { activity.showError(exceptionCaptor.captured) }
        verify(exactly = 1) { activity.hideProgressIndicator() }
        assertThat(exceptionCaptor.captured).isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun employeeLiveData_onSuccess_showsList() {
        val employeeListFragment: EmployeeListFragment = spyk()
        val recyclerView: RecyclerView = mockk(relaxed = true)
        val adapterCaptor = slot<EmployeesListAdapter>()
        val activity = mockk<TestActivity>()

        every { recyclerView.layoutManager = any() } returns Unit
        every { recyclerView.adapter = capture(adapterCaptor) } returns Unit
        every { activity.hideProgressIndicator() } returns Unit
        every { employeeListFragment.activity } returns activity

        employeeListFragment.updateUiState(
            UiState.Success(listOf(mockk(), mockk())),
            recyclerView,
            mockk()
        )

        verify(exactly = 1) { activity.hideProgressIndicator() }
        assertThat(adapterCaptor.captured.itemCount).isEqualTo(2)
    }
}

class TestActivity : AppCompatActivity(), ProgressActivity {
    override fun showProgressIndicator() = Unit

    override fun hideProgressIndicator() = Unit

    override fun showError(throwable: Throwable) = Unit
}