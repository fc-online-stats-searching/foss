package com.foss.foss.feature.home

import com.foss.foss.R

sealed class NavigationItem(val icon: Int, val description: String, val route: String) {

    object RecentMatch : NavigationItem(
        icon = R.drawable.ic_recent_matches,
        description = "최근 전적",
        route = "RECENT MATCH"
    )

    object RelativeMatch : NavigationItem(
        icon = R.drawable.ic_relative_matches,
        description = "상대 전적",
        route = "RELATIVE MATCH"
    )
}
