package com.foss.foss.feature.matchsearching.relativematch.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.foss.foss.feature.matchsearching.relativematch.RelativeMatchRoute

const val RELATIVEMATCH_ROUTE = "RELATIVEMATCH"

fun NavController.navigateToRelativeMatch(navOptions: NavOptions? = null) {
    navigate(RELATIVEMATCH_ROUTE, navOptions)
}

fun NavGraphBuilder.relativeMatchNavGraph(
    onRelativeMatchClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit,
    onBackPressedClick: () -> Unit
) {
    composable(route = RELATIVEMATCH_ROUTE) {
        RelativeMatchRoute(
            onRelativeMatchClick = onRelativeMatchClick,
            onShowSnackBar = onShowSnackBar,
            onBackPressedClick = onBackPressedClick
        )
    }
}
