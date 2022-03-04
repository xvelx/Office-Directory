package com.vm.office.common_ui.state

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.lang.IllegalStateException

@ExperimentalCoroutinesApi
class UiStateFormatterTest {

    private val testScope = TestScope()

    @Test
    fun fromDataRequest_whenRequestBlockThrowsError_returnsErrorState() = testScope.runTest {

        val uiState = fromDataRequest { throw IllegalStateException() }

        assertThat(uiState).isInstanceOf(UiState.Error::class.java)
    }

    @Test
    fun fromDataRequestWithConverter_whenRequestBlockThrowsError_returnsErrorState() =
        testScope.runTest {

            val uiState = fromDataRequest({ throw IllegalStateException() }) {}

            assertThat(uiState).isInstanceOf(UiState.Error::class.java)
        }

    @Test
    fun fromDataRequest_onRequestBlockSuccess_returnsSuccessStateWithData() = testScope.runTest {

        val uiState = fromDataRequest { "Data" }

        assertThat(uiState).isInstanceOf(UiState.Success::class.java)
        assertThat((uiState as? UiState.Success)?.data).isEqualTo("Data")
    }

    @Test
    fun fromDataRequestWithConvertor_onRequestBlockSuccess_returnsSuccessStateWithData() =
        testScope.runTest {
            val sourceData = "Data"

            val uiState = fromDataRequest({ sourceData }) {
                it.length
            }

            assertThat(uiState).isInstanceOf(UiState.Success::class.java)
            assertThat((uiState as? UiState.Success)?.data).isEqualTo(sourceData.length)
        }
}