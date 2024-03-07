package com.foss.foss.model

enum class WinDrawLose {
    WIN,
    DRAW,
    LOSE,
    ;

    companion object {
        fun make(point: Int, otherPoint: Int): WinDrawLose {
            return when {
                point > otherPoint -> WIN
                point < otherPoint -> LOSE
                else -> DRAW
            }
        }
    }
}
