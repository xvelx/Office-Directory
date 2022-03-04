package com.vm.office.common_ui.state

/**
 * Common UI State for UI.
 *
 * An UI (Screen/Fragment) can have one of this state at given time.
 */
sealed class UiState<out T> {

    /**
     * Represents Loading State of UI.
     *
     * `NOTE:` UI should notify that some loading is taking place.
     */
    object Loading : UiState<Nothing>()

    /**
     * Represents Error State of UI.
     *
     * `NOTE:` UI should gracefully handle the error.
     */
    class Error(val throwable: Throwable) : UiState<Nothing>()

    /**
     * Represents Success State of UI.
     *
     * `NOTE:` UI can get the UI Data from the Success state and present it to the user.
     */
    class Success<T>(val data: T) : UiState<T>()
}