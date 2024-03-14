package com.foss.foss.feature.matchsearching.recentmatch

import com.foss.foss.model.MatchUiModel

sealed interface RecentMatchUiState {

    object Default : RecentMatchUiState

    object Empty : RecentMatchUiState

    object Loading : RecentMatchUiState

    data class RecentMatch(val matches: List<MatchUiModel>) : RecentMatchUiState
}
