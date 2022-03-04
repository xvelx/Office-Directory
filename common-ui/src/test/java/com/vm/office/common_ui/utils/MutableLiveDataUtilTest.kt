package com.vm.office.common_ui.utils

import androidx.lifecycle.MutableLiveData
import io.mockk.every
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MutableLiveDataUtilTest {

    @Test
    fun postDistinctValue_whenDataIsUnique_doesNotPostsValue() {
        val mutableLiveData: MutableLiveData<String> = spyk()
        every { mutableLiveData.value } returns "Initial Value"
        every { mutableLiveData.postValue(any()) } returns Unit

        mutableLiveData.postDistinctValue("Initial Value")

        verify(exactly = 0) { mutableLiveData.postValue(any()) }
    }

    @Test
    fun postDistinctValue_whenDataIsNotUnique_postsValue() {
        val valueCaptor = slot<String>()
        val mutableLiveData: MutableLiveData<String> = spyk()
        val newValue = "New Value 2"
        every { mutableLiveData.value } returns "Initial Value"
        every { mutableLiveData.postValue(capture(valueCaptor)) } returns Unit

        mutableLiveData.postDistinctValue(newValue)

        verify(exactly = 1) { mutableLiveData.postValue(any()) }
        assertThat(valueCaptor.captured).isEqualTo(newValue)
    }
}