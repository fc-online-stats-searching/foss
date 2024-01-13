package com.foss.foss.model

import androidx.annotation.StringRes
import com.foss.foss.R

enum class MatchTypeUiModel(
    @StringRes
    val resId: Int,
) {
    ALL(R.string.common_all),
    OFFICIAL(R.string.common_official_description),
    CLASSIC_ONE_TO_ONE(R.string.common_classic_one_to_one),
}
