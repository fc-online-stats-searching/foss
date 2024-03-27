package com.foss.foss.model

import androidx.annotation.DrawableRes
import com.foss.foss.R

enum class DivisionUiModel(
    @DrawableRes
    val symbol: Int
) {
    NONE(R.drawable.ic_tier_none),
    SUPER_CHAMPIONS(R.drawable.ic_tier_super_champions),
    CHAMPIONS(R.drawable.ic_tier_champions),
    SUPER_CHALLENGER(R.drawable.ic_tier_super_challenger),
    CHALLENGER1(R.drawable.ic_tier_challenger1),
    CHALLENGER2(R.drawable.ic_tier_challenger2),
    CHALLENGER3(R.drawable.ic_tier_challenger3),
    WORLD_CLASS1(R.drawable.ic_tier_worldclass1),
    WORLD_CLASS2(R.drawable.ic_tier_worldclass2),
    WORLD_CLASS3(R.drawable.ic_tier_worldclass3),
    PROFESSIONAL1(R.drawable.ic_tier_pro1),
    PROFESSIONAL2(R.drawable.ic_tier_pro2),
    PROFESSIONAL3(R.drawable.ic_tier_pro3),
    SEMI_PROFESSIONAL1(R.drawable.ic_tier_semi1),
    SEMI_PROFESSIONAL2(R.drawable.ic_tier_semi2),
    SEMI_PROFESSIONAL3(R.drawable.ic_tier_semi3),
    PROSPECT1(R.drawable.ic_tier_ama1),
    PROSPECT2(R.drawable.ic_tier_ama2),
    PROSPECT3(R.drawable.ic_tier_ama3);
}
