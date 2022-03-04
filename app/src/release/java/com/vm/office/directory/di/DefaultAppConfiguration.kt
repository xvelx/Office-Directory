package com.vm.office.directory.di

import com.vm.core.network.di.AppNetworkConfiguration
import okhttp3.Interceptor

abstract class DefaultAppConfiguration : AppNetworkConfiguration {

    override fun getInterceptors(): Array<Interceptor> = emptyArray()
}