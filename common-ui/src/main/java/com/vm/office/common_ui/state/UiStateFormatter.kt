package com.vm.office.common_ui.state

/**
 * `Global Method`
 *
 * Handles the requestBlock() and transforms it into an appropriate UiState.
 *
 * @param requestBlock block that contains request to the data source.
 * @return `UiState.Success<T>` in case of success or `UiState.Error` if the data source throws
 * any error.
 */
suspend fun <T> fromDataRequest(
    requestBlock: suspend () -> T,
): UiState<T> {
    return try {
        UiState.Success(requestBlock())
    } catch (exception: Exception) {
        UiState.Error(exception)
    }
}

/**
 * `Global Method`
 *
 * Handles the requestBlock(), transforms the data to K with the help of converter
 * and transforms the K into an appropriate UiState.
 *
 * @param requestBlock block that contains request to the data source.
 * @param converter mapping block that takes T and returns K
 * @return `UiState.Success<T>` in case of success or `UiState.Error` if the data source throws
 * any error.
 */
suspend fun <T, K> fromDataRequest(
    requestBlock: suspend () -> T,
    converter: (data: T) -> K
): UiState<K> {
    return try {
        UiState.Success(converter(requestBlock()))
    } catch (exception: Exception) {
        UiState.Error(exception)
    }
}