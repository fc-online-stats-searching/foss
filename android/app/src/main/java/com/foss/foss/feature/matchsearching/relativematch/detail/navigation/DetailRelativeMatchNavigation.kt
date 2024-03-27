package com.foss.foss.feature.matchsearching.relativematch.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.foss.foss.feature.matchsearching.relativematch.detail.DetailRelativeMatchRoute
import com.foss.foss.model.MatchesUiModel

const val DETAILRELATIVEMATCH_ROUTE = "DETAILRELATIVEMATCH"

fun NavController.navigateToDetailRelativeMatch(
    matchesUiModels: List<MatchesUiModel>,
    navOptions: NavOptions? = null
) {
    currentBackStackEntry?.savedStateHandle?.set("relativeMatch", matchesUiModels)
    navigate(DETAILRELATIVEMATCH_ROUTE, navOptions)
}

fun NavGraphBuilder.detailRelativeMatchNavGraph(
    navController: NavController,
    onBackPressedClick: () -> Unit
) {
    composable(route = DETAILRELATIVEMATCH_ROUTE) {
        val data =
            navController.previousBackStackEntry?.savedStateHandle?.get<List<MatchesUiModel>>(
                "relativeMatch"
            )
        if (data != null) {
            DetailRelativeMatchRoute(matchesUiModels = data, onBackPressedClick = onBackPressedClick)
        } else {
            DetailRelativeMatchRoute(matchesUiModels = emptyList(), onBackPressedClick = onBackPressedClick)
        }
    }
}
