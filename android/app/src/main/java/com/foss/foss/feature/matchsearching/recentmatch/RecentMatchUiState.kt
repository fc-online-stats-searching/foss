package com.foss.foss.feature.matchsearching.recentmatch

import com.foss.foss.model.MatchesUiModel

sealed interface RecentMatchUiState {

    object Default : RecentMatchUiState

    object Empty : RecentMatchUiState

    object Loading : RecentMatchUiState

    data class RecentMatch(val matches: List<MatchesUiModel>) : RecentMatchUiState
}
