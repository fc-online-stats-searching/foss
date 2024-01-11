package com.foss.foss.feature.matchsearching.relative

import com.foss.foss.model.RelativeMatchUiModel

sealed interface RelativeMatchUiState {

    object Empty : RelativeMatchUiState

    object Loading : RelativeMatchUiState

    data class RelativeMatches(val relativeMatches: List<RelativeMatchUiModel>) : RelativeMatchUiState
}
