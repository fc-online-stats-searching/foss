package com.foss.foss.util

sealed class UiState<out T>(val _data: T?) {
    object Loading : UiState<Nothing>(_data = null)
    object Error : UiState<Nothing>(_data = null)
    data class Success<out R>(val data: R) : UiState<R>(_data = data)
}