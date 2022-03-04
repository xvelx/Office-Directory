package com.vm.office.directory.network.di

import com.vm.core.network.di.AppNetworkConfiguration
import com.vm.core.network.di.NetworkModule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Test
import retrofit2.Retrofit

class NetworkModuleTest {

    @Test
    fun provideDefaultNetworkInterfaceBuilder_setsBaseUrlFromConfiguration() {
        val appConfiguration = object : AppNetworkConfiguration {
            override fun getBaseUrl(): String = "https://test.io"

            override fun getInterceptors(): Array<Interceptor> = emptyArray()
        }
        val rBuilder = mockk<Retrofit.Builder>(relaxed = true)

        every { rBuilder.client(any()) } returns rBuilder
        every { rBuilder.baseUrl(any<String>()) } returns rBuilder

        NetworkModule().provideDefaultNetworkInterfaceBuilder(rBuilder, mockk(), appConfiguration)

        verify(exactly = 1) {
            rBuilder.baseUrl(appConfiguration.getBaseUrl())
        }
    }

    @Test
    fun provideDefaultNetworkInterfaceBuilder_setsGivenOkHttpClient() {
        val mockedOkHttpClient = mockk<OkHttpClient>()
        val rBuilder = mockk<Retrofit.Builder>(relaxed = true)

        every { rBuilder.client(any()) } returns rBuilder

        NetworkModule().provideDefaultNetworkInterfaceBuilder(
            rBuilder,
            mockedOkHttpClient,
            mockk(relaxed = true)
        )

        verify(exactly = 1) {
            rBuilder.client(mockedOkHttpClient)
        }
    }

    @Test
    fun provideOkHttpClient_whenNoInterceptorConfigured_addsNoInterceptor() {
        val appConfiguration = object : AppNetworkConfiguration {
            override fun getBaseUrl(): String = "https://test.io"

            override fun getInterceptors(): Array<Interceptor> = emptyArray()
        }
        val clientBuilder = mockk<OkHttpClient.Builder>(relaxed = true)

        every { clientBuilder.addInterceptor(any()) } returns clientBuilder

        NetworkModule().provideOkHttpClient(clientBuilder, appConfiguration)

        verify(exactly = 0) {
            clientBuilder.addInterceptor(any())
        }
    }

    @Test
    fun provideOkHttpClient_whenInterceptorsConfigured_addsAllInterceptor() {
        val appConfiguration = object : AppNetworkConfiguration {
            override fun getBaseUrl(): String = "https://test.io"

            override fun getInterceptors(): Array<Interceptor> = arrayOf(mockk(), mockk())
        }
        val clientBuilder = mockk<OkHttpClient.Builder>(relaxed = true)

        every { clientBuilder.addInterceptor(any()) } returns clientBuilder

        NetworkModule().provideOkHttpClient(clientBuilder, appConfiguration)

        verify(exactly = 2) {
            clientBuilder.addInterceptor(any())
        }
    }
}