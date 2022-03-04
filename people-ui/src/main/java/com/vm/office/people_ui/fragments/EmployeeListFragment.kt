package com.vm.office.people_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vm.office.common_ui.fragments.BaseFragment
import com.vm.office.common_ui.state.UiState
import com.vm.office.common_ui.utils.handleUiState
import com.vm.office.people_ui.adapters.EmployeesListAdapter
import com.vm.office.people_ui.databinding.FragmentEmployeeListBinding
import com.vm.office.people_ui.states.data.EmployeeUiData
import com.vm.office.people_ui.vms.EmployeesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Presents all the employees list to the user.
 */
@AndroidEntryPoint
class EmployeeListFragment : BaseFragment<FragmentEmployeeListBinding>() {

    private companion object {
        const val EMPLOYEE_LIST_SPAN_COUNT = 2
    }

    private val employeesViewModel: EmployeesViewModel by activityViewModels()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentEmployeeListBinding = FragmentEmployeeListBinding
        .inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSearchView()
        configureEmployeeListView()
    }

    private fun configureSearchView() {
        viewBinding.peopleSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    employeesViewModel.searchEmployees(newText)
                    return true
                }

                return false
            }
        })
    }

    private fun configureEmployeeListView() {
        employeesViewModel.employeesLiveData.observe(viewLifecycleOwner) {
            updateUiState(it, viewBinding.employeeListRecyclerView)
        }
        employeesViewModel.fetchEmployees()
    }

    private val employeeListGridLayoutManager: GridLayoutManager
        get() = GridLayoutManager(
            context,
            EMPLOYEE_LIST_SPAN_COUNT,
            GridLayoutManager.VERTICAL,
            false
        )

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun updateUiState(
        uiState: UiState<List<EmployeeUiData>>,
        recyclerView: RecyclerView,
        layoutManager: GridLayoutManager = employeeListGridLayoutManager
    ) {
        handleUiState(uiState) { employeeList ->
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = EmployeesListAdapter(employeeList)
        }
    }
}