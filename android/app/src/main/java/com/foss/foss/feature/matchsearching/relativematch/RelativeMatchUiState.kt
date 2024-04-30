package com.foss.foss.feature.matchsearching.relativematch

import com.foss.foss.model.MatchesUiModel
import com.foss.foss.model.RelativeMatchMapper.toMatchesUiModel
import com.foss.foss.model.RelativeMatchUiModel

sealed interface RelativeMatchUiState {

    object Default : RelativeMatchUiState

    object Empty : RelativeMatchUiState

    object Loading : RelativeMatchUiState

    data class RelativeMatches(
        val relativeMatches: List<RelativeMatchUiModel>
    ) : RelativeMatchUiState {

        var opponentName: String = ""
            private set
        var showingMatchDetails: List<MatchesUiModel> = listOf()
            private set

        fun updateShowingMatchDetails(opponentNickname: String) {
            opponentName = opponentNickname
            showingMatchDetails =
                relativeMatches.find { it.opponentName == opponentName }
                    ?.matchDetails
                    ?.toMatchesUiModel()
                    ?: throw NoSuchElementException(UPDATE_SHOWING_MATCH_DETAILS_ERROR)
        }
    }

    companion object {

        private const val UPDATE_SHOWING_MATCH_DETAILS_ERROR = "해당 닉네임을 가진 유저와의 상대전적을 찾을 수 없습니다."
    }
}
