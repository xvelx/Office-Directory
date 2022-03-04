package com.vm.core.concurrency.di

import javax.inject.Qualifier

/**
 * Named Qualifier to identify the IO Dispatcher.
 */
@Qualifier
annotation class IoDispatcher

/**
 * Named Qualifier to identify the Main Dispatcher.
 */
@Qualifier
annotation class MainDispatcher