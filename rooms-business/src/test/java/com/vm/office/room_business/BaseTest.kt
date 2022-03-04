package com.vm.office.room_business

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before

@ExperimentalCoroutinesApi
open class BaseTest {

    protected val testScope = TestScope()
    protected val testDispatcher = UnconfinedTestDispatcher()

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
    }
}