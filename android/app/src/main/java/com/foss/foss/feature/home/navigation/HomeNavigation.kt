package com.foss.foss.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.foss.foss.feature.home.HomeScreen

const val HOME_ROUTE = "HOME"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    onRelativeStatButtonClick: () -> Unit,
    onRecentStatButtonClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(
            onShowSnackBar = onShowSnackBar,
            onRelativeStatButtonClick = onRelativeStatButtonClick,
            onRecentStatButtonClick = onRecentStatButtonClick
        )
    }
}
