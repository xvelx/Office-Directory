package com.vm.office.people_business.repositories

import androidx.annotation.VisibleForTesting
import com.vm.core.concurrency.di.IoDispatcher
import com.vm.office.people_business.dtos.Employee
import com.vm.office.people_business.sources.EmployeesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Abstracts the employee data source and provides the use-able methods to
 * fetch data required.
 */
class EmployeesRepository @Inject constructor(
    private val dataSource: EmployeesDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val employeeMutex = Mutex()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var employeeList = emptyList<Employee>()

    /**
     * Fetches the employee list from the Employee Data Source.
     *
     * @return list of Employee
     */
    suspend fun fetchEmployees() = withContext(dispatcher) {
        if (employeeList.isEmpty()) {

            employeeMutex.withLock {
                employeeList = dataSource.getEmployees()
            }
        }

        return@withContext employeeMutex.withLock { employeeList }
    }

    /**
     * Searches the employee with given query string.
     * Query string matches against firstName, lastName and jobTitle and returns the result.
     *
     * @return matched list of Employee
     */
    suspend fun searchEmployees(query: String) = withContext(dispatcher) {
        return@withContext employeeList.filter { employee ->
            return@filter (employee.firstName.contains(query, ignoreCase = true)
                    || employee.lastName.contains(query, ignoreCase = true)
                    || employee.jobTitle.contains(query, ignoreCase = true))
        }
    }
}