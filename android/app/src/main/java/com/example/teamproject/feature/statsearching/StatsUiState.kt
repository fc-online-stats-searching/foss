package com.example.teamproject.feature.statsearching

import com.example.teamproject.model.MatchResultUiModel

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
