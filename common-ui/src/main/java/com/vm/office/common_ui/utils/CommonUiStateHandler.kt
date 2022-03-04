package com.vm.office.common_ui.utils

import androidx.fragment.app.Fragment
import com.vm.office.common_ui.activities.ProgressActivity
import com.vm.office.common_ui.state.UiState

/**
 * Utility method to handle common Ui States globally. (i.e. Loading, Error)
 *
 * @param uiState state to handle.
 */
private fun <T> Fragment.handleCommonUiState(uiState: UiState<T>) {
    when (uiState) {
        is UiState.Loading -> {
            (activity as? ProgressActivity)?.showProgressIndicator()
        }
        is UiState.Error -> {
            (activity as? ProgressActivity)?.hideProgressIndicator()
            (activity as? ProgressActivity)?.showError(uiState.throwable)
        }
        else -> {
            // No Operation
        }
    }
}

/**
 * Utility method to handle common Ui States globally. (i.e. Loading, Error)
 * Invokes success call if the state is of UiState.Success<T>.
 *
 * @param uiState state to handle.
 */
fun <T> Fragment.handleUiState(
    uiState: UiState<T>,
    onSuccess: (successState: T) -> Unit
) {
    handleCommonUiState(uiState)
    if (uiState is UiState.Success<T>) {
        onSuccess(uiState.data)
        (activity as? ProgressActivity)?.hideProgressIndicator()
    }
}