package com.vm.office.room_business.di

import com.vm.office.room_business.sources.RoomsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module provides the Room entities that required for DI
 */
@InstallIn(SingletonComponent::class)
@Module
class RoomsModule {

    /**
     * Provides Room Data Source
     *
     * @param clientBuilder retrofit instance. This is an injected value.
     */
    @Singleton
    @Provides
    fun provideDataSource(clientBuilder: Retrofit): RoomsDataSource =
        clientBuilder.create(RoomsDataSource::class.java)
}