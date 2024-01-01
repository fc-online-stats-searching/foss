package com.foss.foss.feature.matchsearching.recent

import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchUiModel

sealed interface RecentMatchesUiState {

    data class Default(val matchTypes: List<MatchTypeUiModel>) : RecentMatchesUiState

    object Loading : RecentMatchesUiState

    data class Success(val data: List<MatchUiModel>) : RecentMatchesUiState
}
