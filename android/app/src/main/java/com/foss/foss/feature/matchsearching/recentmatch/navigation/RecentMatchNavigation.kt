package com.foss.foss.feature.matchsearching.recentmatch.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.foss.foss.feature.matchsearching.recentmatch.RecentMatchRoute

const val RECENTMATCH_ROUTE = "RECENTMATCH"

fun NavController.navigateToRecentMatch(navOptions: NavOptions? = null) {
    navigate(RECENTMATCH_ROUTE, navOptions)
}

fun NavGraphBuilder.recentMatchNavGraph(
    onBackPressedClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = RECENTMATCH_ROUTE) {
        RecentMatchRoute(
            onBackPressedClick = onBackPressedClick,
            onShowSnackBar = onShowSnackBar
        )
    }
}
