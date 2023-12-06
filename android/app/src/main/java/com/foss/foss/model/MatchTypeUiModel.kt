package com.foss.foss.model

import androidx.annotation.StringRes
import com.foss.foss.R

enum class MatchTypeUiModel(
    @StringRes
    val resId: Int
) {
    LEAGUE_FRIENDLY(R.string.common_league_friendly_description),
    CLASSIC_ONE_TO_ONE(R.string.common_classic_one_to_one),
    OFFICIAL(R.string.common_official_description),
    DIRECTOR(R.string.common_director_description),
    OFFICIAL_FRIENDLY(R.string.common_official_friendly),
    VOLTA_FRIENDLY(R.string.common_volta_friendly_description),
    VOLTA_OFFICIAL(R.string.common_volta_offical_description),
    VOLTA_CUSTOM(R.string.common_volta_custom_description)
}
