package com.foss.foss.feature.matchsearching.relativematch

import com.foss.foss.model.RelativeMatchUiModel

sealed interface RelativeMatchUiState {

    object Default : RelativeMatchUiState

    object Empty : RelativeMatchUiState

    object Loading : RelativeMatchUiState

    data class RelativeMatches(val relativeMatches: List<RelativeMatchUiModel>) :
        RelativeMatchUiState
}
