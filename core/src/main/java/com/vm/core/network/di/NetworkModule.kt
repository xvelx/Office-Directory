package com.vm.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Includes Network components to the DI
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    /**
     * Provides the client builder (Retrofit).
     *
     * @param configuration configuration that contains required values
     * to initialise the client builder.
     * @return client builder (Retrofit)
     */
    @Singleton
    @Provides
    fun provideDefaultNetworkInterfaceBuilder(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
        configuration: AppNetworkConfiguration
    ): Retrofit = retrofitBuilder.client(okHttpClient)
        .baseUrl(configuration.getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    /**
     *
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        clientBuilder: OkHttpClient.Builder,
        configuration: AppNetworkConfiguration
    ): OkHttpClient = clientBuilder
        .apply { configuration.getInterceptors().forEach { addInterceptor(it) } }
        .build()

    /**
     * Provides Retrofit Builder.
     *
     * This is to avoid complex mocking for unit tests.
     */
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    /**
     * Provides OkHttpClient Builder.
     *
     * This is to avoid complex mocking for unit tests.
     */
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

}