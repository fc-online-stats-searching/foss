package com.foss.foss.model

import com.foss.foss.model.DivisionMapper.toUiModel
import java.time.LocalDate

object MatchMapper {

    /**
     * Match를 날짜 별로 정리해 MatchesUiModel로 mapping한다.
     * RecentScreen을 위한 mapping 함수
     */
    fun List<Match>.toUiModel(): List<MatchesUiModel> {
        val matches: MutableMap<LocalDate, ArrayList<Match>> = mutableMapOf()

        forEach { match ->
            if (!matches.containsKey(match.date.toLocalDate())) {
                matches[match.date.toLocalDate()] =
                    arrayListOf()
            }
            matches[match.date.toLocalDate()]?.add(match)
        }
        return matches.map {
            MatchesUiModel(
                date = it.key,
                value = it.value.map { match ->
                    match.toUiModel()
                }
            )
        }.sortedByDescending { matchesUiModel ->
            matchesUiModel.date
        }
    }

    fun Match.toUiModel() = MatchUiModel(
        date = date,
        manOfTheMatch = manOfTheMatch,
        matchType = matchType.toUiModel(),
        opponentName = opponentName,
        opponentDivision = opponentDivision.toUiModel(),
        winDrawLose = winDrawLose.toUiModel(),
        point = score.point,
        otherPoint = score.otherPoint
    )

    fun MatchType.toUiModel() = when (this) {
        MatchType.CLASSIC_ONE_TO_ONE -> MatchTypeUiModel.CLASSIC_ONE_TO_ONE
        MatchType.OFFICIAL -> MatchTypeUiModel.OFFICIAL
        MatchType.ALL -> MatchTypeUiModel.ALL
    }

    fun MatchTypeUiModel.toDomainModel() = when (this) {
        MatchTypeUiModel.CLASSIC_ONE_TO_ONE -> MatchType.CLASSIC_ONE_TO_ONE
        MatchTypeUiModel.OFFICIAL -> MatchType.OFFICIAL
        MatchTypeUiModel.ALL -> MatchType.ALL
    }

    fun WinDrawLose.toUiModel() = when (this) {
        WinDrawLose.WIN -> WinDrawLoseUiModel.WIN
        WinDrawLose.DRAW -> WinDrawLoseUiModel.DRAW
        WinDrawLose.LOSE -> WinDrawLoseUiModel.LOSE
    }

    fun MatchType.toIntType() = when (this) {
        MatchType.OFFICIAL -> 50
        MatchType.CLASSIC_ONE_TO_ONE -> 40
        MatchType.ALL -> 10
        else -> 10
    }
}
