package com.vm.office.room_ui.fragments

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.vm.office.common_ui.activities.ProgressActivity
import com.vm.office.common_ui.state.UiState
import com.vm.office.room_ui.adapters.RoomListAdapter
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RoomListFragmentTest {

    @Test
    fun roomLiveData_onLoadingState_callsShowLoading() {
        val roomListFragment: RoomListFragment = spyk()
        val activity = mockk<TestActivity>()

        every { activity.showProgressIndicator() } returns Unit
        every { roomListFragment.activity } returns activity

        roomListFragment.updateUiState(UiState.Loading, mockk(), mockk())

        verify(exactly = 1) { activity.showProgressIndicator() }
    }

    @Test
    fun roomLiveData_onErrorState_callsShowError() {
        val roomListFragment: RoomListFragment = spyk()
        val exceptionCaptor = slot<Throwable>()
        val activity = mockk<TestActivity>()

        every { activity.showError(capture(exceptionCaptor)) } returns Unit
        every { activity.hideProgressIndicator() } returns Unit
        every { roomListFragment.activity } returns activity

        roomListFragment.updateUiState(UiState.Error(IllegalStateException()), mockk(), mockk())

        verify(exactly = 1) { activity.showError(exceptionCaptor.captured) }
        verify(exactly = 1) { activity.hideProgressIndicator() }
        assertThat(exceptionCaptor.captured).isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun roomLiveData_onSuccess_showsList() {
        val roomListFragment: RoomListFragment = spyk()
        val recyclerView: RecyclerView = mockk(relaxed = true)
        val adapterCaptor = slot<RoomListAdapter>()
        val activity = mockk<TestActivity>()

        every { recyclerView.layoutManager = any() } returns Unit
        every { recyclerView.adapter = capture(adapterCaptor) } returns Unit
        every { activity.hideProgressIndicator() } returns Unit
        every { roomListFragment.activity } returns activity

        roomListFragment.updateUiState(
            UiState.Success(listOf(mockk(), mockk())),
            recyclerView,
            mockk()
        )

        verify(exactly = 1) { activity.hideProgressIndicator() }
        assertThat(adapterCaptor.captured.itemCount).isEqualTo(2)
    }
}

class TestActivity : AppCompatActivity(), ProgressActivity {
    override fun showProgressIndicator() = Unit

    override fun hideProgressIndicator() = Unit

    override fun showError(throwable: Throwable) = Unit
}