package com.foss.foss.feature.matchsearching.recent

import com.foss.foss.model.MatchUiModel

sealed interface RecentMatchesUiState {

    object Empty : RecentMatchesUiState

    object Loading : RecentMatchesUiState

    data class RecentMatches(val matches: List<MatchUiModel>) : RecentMatchesUiState
}
