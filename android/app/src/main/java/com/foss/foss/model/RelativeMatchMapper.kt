package com.foss.foss.model

import com.foss.foss.model.DivisionMapper.toUiModel
import com.foss.foss.model.MatchMapper.toUiModel
import java.time.LocalDate

object RelativeMatchMapper {

    fun RelativeMatch.toUiModel(): RelativeMatchUiModel = RelativeMatchUiModel(
        opponentName = opponentName,
        numberOfGames = winDrawLoses.numberOfGames,
        numberOfWins = winDrawLoses.numberOfWins,
        numberOfDraws = winDrawLoses.numberOfDraws,
        numberOfLoses = winDrawLoses.numberOfLoses,
        recentMatchDate = recentMatchDate,
        goal = totalScore.point,
        conceded = totalScore.otherPoint,
        divisionUiModel = opponentDivision.toUiModel(),
        matchDetails = matchDetails.map { it.toUiModel() }
    )

    fun List<MatchUiModel>.toMatchesUiModel(): List<MatchesUiModel> {
        val matches: MutableMap<LocalDate, ArrayList<MatchUiModel>> = mutableMapOf()

        forEach { match ->
            if (!matches.containsKey(match.date.toLocalDate())) matches[match.date.toLocalDate()] = arrayListOf()
            matches[match.date.toLocalDate()]?.add(match)
        }

        return matches.map {
            MatchesUiModel(
                date = it.key,
                value = it.value
            )
        }.sortedByDescending { matchesUiModel ->
            matchesUiModel.date
        }
    }
}
