package com.vm.office.directory.di

import com.vm.core.network.di.AppNetworkConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides App Configuration required for the Development build
 */
@InstallIn(SingletonComponent::class)
@Module
class AppConfigurationModule {

    /**
     * Provides Network Configuration required for the App
     */
    @Singleton
    @Provides
    fun networkConfiguration(): AppNetworkConfiguration = object : DefaultAppConfiguration() {
        override fun getBaseUrl(): String = "https://61e947967bc0550017bc61bf.mockapi.io/api/v1/"
    }
}