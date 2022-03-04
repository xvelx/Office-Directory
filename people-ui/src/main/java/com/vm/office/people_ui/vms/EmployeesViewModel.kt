package com.vm.office.people_ui.vms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vm.office.common_ui.state.UiState
import com.vm.office.common_ui.state.fromDataRequest
import com.vm.office.common_ui.utils.postDistinctValue
import com.vm.office.people_business.repositories.EmployeesRepository
import com.vm.office.people_ui.states.data.EmployeeUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * State Holder of Employee UI.
 *
 * Interacts with the Employee Repository when necessary to get the data and publishes
 * the new required UiState.
 */
@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val employeesRepository: EmployeesRepository,
) : ViewModel() {

    private val mutableEmployeesLiveData = MutableLiveData<UiState<List<EmployeeUiData>>>()
    val employeesLiveData: LiveData<UiState<List<EmployeeUiData>>> = mutableEmployeesLiveData

    /**
     * Fetches the Employee list.
     *
     * On completion - new UiState would be published that can be observed through
     * `employeesLiveData`
     */
    fun fetchEmployees() {
        mutableEmployeesLiveData.value = UiState.Loading
        viewModelScope.launch {
            val employeeUiState = fromDataRequest({ employeesRepository.fetchEmployees() }) {
                EmployeeUiData.fromEmployeeList(it)
            }
            mutableEmployeesLiveData.postDistinctValue(employeeUiState)
        }
    }

    /**
     * Searches the Employee list.
     *
     * On completion - new UiState would be published that can be observed through
     * `employeesLiveData`
     */
    fun searchEmployees(query: String) {
        mutableEmployeesLiveData.value = UiState.Loading
        viewModelScope.launch {
            val filteredEmployeesUiState =
                fromDataRequest({ employeesRepository.searchEmployees(query) }) {
                    EmployeeUiData.fromEmployeeList(it)
                }
            mutableEmployeesLiveData.postDistinctValue(filteredEmployeesUiState)
        }
    }
}