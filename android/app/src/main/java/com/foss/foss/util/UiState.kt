package com.foss.foss.util

sealed class HomeUiState<out T>(val _data: T?) {
    object Loading : HomeUiState<Nothing>(null)
    object Error : HomeUiState<Nothing>(null)
    data class Success<out R>(val data: R) : HomeUiState<R>(data)
}

sealed class RecentMatchesUiState<out T>(val _data: T?) {
    object Loading : RecentMatchesUiState<Nothing>(null)
    object Error : RecentMatchesUiState<Nothing>(null)
    data class Success<out R>(val data: R) : RecentMatchesUiState<R>(data)
}