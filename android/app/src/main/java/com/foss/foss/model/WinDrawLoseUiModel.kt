package com.foss.foss.model

import androidx.annotation.StringRes
import com.foss.foss.R

enum class WinDrawLoseUiModel(
    @StringRes
    val resId: Int,
) {
    WIN(R.string.common_win_description),
    DRAW(R.string.common_draw_description),
    LOSE(R.string.common_lose_description),
    ;

    companion object {
        fun WinDrawLoseUiModel.getColorResId(): Int {
            return when (this) {
                WIN -> R.color.blue
                DRAW -> R.color.foss_gray800
                LOSE -> R.color.foss_red
            }
        }

        fun WinDrawLoseUiModel.getStringResId(): Int {
            return when (this) {
                WIN -> R.string.recent_match_win
                DRAW -> R.string.recent_match_draw
                LOSE -> R.string.recent_match_lose
            }
        }
    }
}
