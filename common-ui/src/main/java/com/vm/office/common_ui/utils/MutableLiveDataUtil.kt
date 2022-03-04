package com.vm.office.common_ui.utils

import androidx.lifecycle.MutableLiveData

/**
 * Extension for MutableLiveData
 *
 * This helps to post only the distinct values.
 */
fun <T> MutableLiveData<T>.postDistinctValue(newValue: T) {
    if (value != newValue) {
        postValue(newValue)
    }
}