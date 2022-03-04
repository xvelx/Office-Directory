package com.vm.office.people_business.sources

import com.vm.office.people_business.dtos.Employee
import retrofit2.http.GET

/**
 * Employee Data Source
 *
 * Retrofit API Interface
 */
interface EmployeesDataSource {

    /**
     * Non-Paginated API
     *
     * @return list of employee in the system.
     */
    @GET("/people")
    suspend fun getEmployees(): List<Employee>
}