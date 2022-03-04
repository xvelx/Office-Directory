package com.vm.office.people_business.di

import com.vm.office.people_business.sources.EmployeesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module provides the Employee entities that required for DI
 */
@InstallIn(SingletonComponent::class)
@Module
class EmployeesModule {

    /**
     * Provides Employee Data Source
     *
     * @param clientBuilder retrofit instance. This is an injected value.
     */
    @Singleton
    @Provides
    fun provideDataSource(clientBuilder: Retrofit): EmployeesDataSource =
        clientBuilder.create(EmployeesDataSource::class.java)
}