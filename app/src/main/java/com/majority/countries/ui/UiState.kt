package com.majority.countries.ui

sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    data class Error<T>(val throwable: Throwable) : UiState<T>()
    data class Content<T>(val data: T) : UiState<T>()
}
