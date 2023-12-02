package com.foss.foss.model

data class RelativeStatsUiModel(
    val opponentName: String,
    val matchResult: String,
    val lastMatchData: String,
    val goal: Int,
    val conceded: Int,
) {
    companion object {
        val mockDatas = List(5) {
            RelativeStatsUiModel(
                opponentName = "Opponent $it",
                matchResult = "5전 3승 2패",
                lastMatchData = "최근 경기 2021.09.01",
                goal = 3,
                conceded = 1,
            )
        }
    }
}
