package com.foss.foss.model

class MatchesResult(
    val value: List<MatchResult>
) {

    val numberOfMatch: Int = value.size

    val numberOfWin: Int = value.count {
        it.winDrawLose == WinDrawLose.WIN
    }

    val numberOfDraw: Int = value.count {
        it.winDrawLose == WinDrawLose.DRAW
    }

    val numberOfLose: Int = value.count {
        it.winDrawLose == WinDrawLose.LOSE
    }

    val winningRate: Int = (numberOfWin / numberOfMatch) * 100
}
