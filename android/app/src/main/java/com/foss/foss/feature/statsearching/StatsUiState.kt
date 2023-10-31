package com.foss.foss.feature.statsearching

import com.foss.foss.model.MatchResultUiModel

sealed interface StatsUiState {

    data class Matches(
        val matchesResult: List<MatchResultUiModel>,
        val numberOfWin: Int,
        val numberOfDraw: Int,
        val numberOfLose: Int,
        val winningRate: Int
    ) : StatsUiState

    object Loading : StatsUiState
}
