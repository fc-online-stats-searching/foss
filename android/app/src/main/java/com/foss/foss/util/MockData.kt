package com.foss.foss.util

import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.WinDrawLoseUiModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object MockData {
    private val nnow = LocalDateTime.now()
        .truncatedTo(ChronoUnit.MINUTES)
        .toString()

    private val now = LocalDateTime.parse(nnow, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))

    val recentMatch = listOf<MatchUiModel>(
        MatchUiModel(
            date = now.minusMinutes(30),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLoseUiModel.WIN,
            point = 1,
            otherPoint = 0,
        ),
        MatchUiModel(
            date = now.minusHours(1),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLoseUiModel.WIN,
            point = 1,
            otherPoint = 0,
        ),
        MatchUiModel(
            date = now.minusHours(2),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
        MatchUiModel(
            date = now.minusHours(23).minusMinutes(59),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLoseUiModel.WIN,
            point = 1,
            otherPoint = 0,
        ),
        MatchUiModel(
            date = now.minusDays(1),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
        MatchUiModel(
            date = now.minusDays(2),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
        MatchUiModel(
            date = now.minusDays(3),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
    )
}
