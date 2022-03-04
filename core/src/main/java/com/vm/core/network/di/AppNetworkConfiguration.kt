package com.vm.core.network.di

import okhttp3.Interceptor

/**
 * Contract of App Network Configuration that should be provided in the app layer
 * in order to set up the network client.
 */
interface AppNetworkConfiguration {

    /**
     * @return Base URL of the environment.
     */
    fun getBaseUrl(): String

    fun getInterceptors(): Array<Interceptor>
}