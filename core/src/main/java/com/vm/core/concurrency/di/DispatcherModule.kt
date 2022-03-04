package com.vm.core.concurrency.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provides concurrency entities that has to be part of DI.
 */
@InstallIn(ViewModelComponent::class)
@Module
class DispatcherModule {

    /**
     * Provides IO CoroutineDispatcher
     *
     * @return Dispatchers.IO
     */
    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * Provides Main CoroutineDispatcher
     *
     * @return Dispatchers.Main
     */
    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

