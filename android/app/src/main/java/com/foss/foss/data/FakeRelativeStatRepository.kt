package com.foss.foss.data

import com.foss.foss.model.Nickname
import com.foss.foss.model.RelativeStat
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.WinDrawLoses
import com.foss.foss.model.legacy.Score
import com.foss.foss.repository.RelativeStatsRepository
import java.time.LocalDate

class FakeRelativeStatRepository : RelativeStatsRepository {

    override fun fetchRelativeStats(nickname: Nickname): Result<List<RelativeStat>> = runCatching {
        listOf(
            RelativeStat(
                Nickname("Clinton Hinton"),
                recentMatchDate = LocalDate.of(2023, 12, 2),
                winDrawLoses = WinDrawLoses(
                    listOf(
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.LOSE,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                    ),
                ),
                totalScore = Score(point = 31, otherPoint = 23),
            ),
            RelativeStat(
                Nickname("신공학관캣대디"),
                recentMatchDate = LocalDate.of(2023, 12, 2),
                winDrawLoses = WinDrawLoses(
                    listOf(
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.LOSE,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                    ),
                ),
                totalScore = Score(point = 31, otherPoint = 23),
            ),
            RelativeStat(
                Nickname("신공학관캣맘"),
                recentMatchDate = LocalDate.of(2023, 12, 2),
                winDrawLoses = WinDrawLoses(
                    listOf(
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.LOSE,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                    ),
                ),
                totalScore = Score(point = 31, otherPoint = 23),
            ),
        )
    }
}
