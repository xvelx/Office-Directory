package com.vm.office.common_ui.utils

import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.vm.office.common_ui.activities.ProgressActivity
import com.vm.office.common_ui.fragments.BaseFragment
import com.vm.office.common_ui.state.UiState
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CommonUiStateHandlerTest {

    @Test
    fun handleUiState_whenStateIsLoading_invokesShowProgressIndicator() {
        var invokedSuccessBlock = false
        val mockedFragment = spyk<BaseFragment<ViewBinding>>()
        val mockedActivity = mockk<TestActivity>()
        every { mockedActivity.showProgressIndicator() } returns Unit
        every { mockedFragment.activity } returns mockedActivity

        mockedFragment.handleUiState(UiState.Loading) {
            invokedSuccessBlock = true
        }

        assertThat(invokedSuccessBlock).isFalse
        verify(exactly = 1) {
            mockedActivity.showProgressIndicator()
        }
    }

    @Test
    fun handleUiState_whenStateIsError_invokesShowError() {
        var invokedSuccessBlock = false
        val errorCaptor = slot<Throwable>()
        val mockedFragment = spyk<BaseFragment<ViewBinding>>()
        val mockedActivity = mockk<TestActivity>()
        every { mockedActivity.hideProgressIndicator() } returns Unit
        every { mockedActivity.showError(capture(errorCaptor)) } returns Unit
        every { mockedFragment.activity } returns mockedActivity

        mockedFragment.handleUiState(UiState.Error(IllegalStateException())) {
            invokedSuccessBlock = true
        }

        assertThat(invokedSuccessBlock).isFalse
        verify(exactly = 1) {
            mockedActivity.showError(errorCaptor.captured)
        }
        verify(exactly = 1) {
            mockedActivity.hideProgressIndicator()
        }
    }

    @Test
    fun handleUiState_whenStateIsSuccess_invokesSuccessBlock() {
        val successData = "SUCCESS"
        var dataCaptor: String? = null

        val mockedFragment = spyk<BaseFragment<ViewBinding>>()
        val mockedActivity = mockk<TestActivity>()
        every { mockedActivity.hideProgressIndicator() } returns Unit
        every { mockedFragment.activity } returns mockedActivity

        mockedFragment.handleUiState<String>(UiState.Success(successData)) {
            dataCaptor = it
        }

        assertThat(dataCaptor).isEqualTo(successData)
        verify(exactly = 1) {
            mockedActivity.hideProgressIndicator()
        }
    }
}

internal abstract class TestActivity : FragmentActivity(), ProgressActivity