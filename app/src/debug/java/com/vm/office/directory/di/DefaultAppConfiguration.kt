package com.vm.office.directory.di

import com.vm.core.network.di.AppNetworkConfiguration
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

abstract class DefaultAppConfiguration : AppNetworkConfiguration {

    override fun getInterceptors(): Array<Interceptor> = arrayOf(
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }
    )
}