package com.foss.foss.model

import androidx.annotation.StringRes
import com.foss.foss.R

enum class WinDrawLoseUiModel(
    @StringRes
    val resId: Int
) {
    WIN(R.string.common_win_description),
    DRAW(R.string.common_draw_description),
    LOSE(R.string.common_lose_description)
}
