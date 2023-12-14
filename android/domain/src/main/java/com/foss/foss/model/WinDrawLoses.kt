package com.foss.foss.model

data class WinDrawLoses(
    private val value: List<WinDrawLose>
) {

    val numberOfGames = value.size
    val numberOfWins = value.count { it == WinDrawLose.WIN }
    val numberOfDraws = value.count { it == WinDrawLose.DRAW }
    val numberOfLoses = value.count { it == WinDrawLose.LOSE }
}
