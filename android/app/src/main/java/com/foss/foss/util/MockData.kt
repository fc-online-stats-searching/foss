package com.foss.foss.util

import com.foss.foss.model.Match
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchType
import com.foss.foss.model.MatchesUiModel
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object MockData {
    private val nnow = LocalDateTime.now()
        .truncatedTo(ChronoUnit.MINUTES)
        .toString()

    private val now = LocalDateTime.parse(nnow, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))

    val recentMatch: List<MatchesUiModel> = listOf(
        Match(
            date = now.minusMinutes(30),
            matchType = MatchType.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLose.WIN,
            Score(
                point = 1,
                otherPoint = 0
            )
        ),
        Match(
            date = now.minusHours(1),
            matchType = MatchType.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLose.WIN,
            Score(
                point = 1,
                otherPoint = 0
            )
        ),
        Match(
            date = now.minusHours(2),
            matchType = MatchType.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLose.LOSE,
            Score(
                point = 0,
                otherPoint = 1
            )
        ),
        Match(
            date = now.minusHours(23).minusMinutes(59),
            matchType = MatchType.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLose.WIN,
            Score(
                point = 1,
                otherPoint = 0
            )
        ),
        Match(
            date = now.minusDays(1),
            matchType = MatchType.CLASSIC_ONE_TO_ONE,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLose.LOSE,
            Score(
                point = 0,
                otherPoint = 1
            )
        ),
        Match(
            date = now.minusDays(2),
            matchType = MatchType.CLASSIC_ONE_TO_ONE,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLose.LOSE,
            Score(
                point = 0,
                otherPoint = 1
            )
        ),
        Match(
            date = now.minusDays(3),
            matchType = MatchType.CLASSIC_ONE_TO_ONE,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLose.LOSE,
            Score(
                point = 0,
                otherPoint = 1
            )
        )
    ).toUiModel()
}
