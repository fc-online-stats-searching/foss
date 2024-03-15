package com.foss.foss.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.foss.foss.feature.matchsearching.recentmatch.RecentMatchScreen

const val RECENTMATCH_ROUTE = "RECENTMATCH"

fun NavController.navigateToRecentMatch(navOptions: NavOptions? = null) {
    navigate(RECENTMATCH_ROUTE, navOptions)
}

fun NavGraphBuilder.recentMatchNavGraph(
    onBackPressedClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    composable(route = RECENTMATCH_ROUTE) {
        RecentMatchScreen(
            onBackPressedClick = onBackPressedClick,
            onRefreshClick = onRefreshClick
        )
    }
}
