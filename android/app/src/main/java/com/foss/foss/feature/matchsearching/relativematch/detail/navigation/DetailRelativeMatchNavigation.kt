package com.foss.foss.feature.matchsearching.relativematch.detail.navigation

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.foss.foss.feature.matchsearching.relativematch.RelativeMatchViewModel
import com.foss.foss.feature.matchsearching.relativematch.detail.DetailRelativeMatchRoute
import com.foss.foss.feature.matchsearching.relativematch.navigation.RELATIVEMATCH_ROUTE

const val DETAILRELATIVEMATCH_ROUTE = "DETAILRELATIVEMATCH"

fun NavController.navigateToDetailRelativeMatch(navOptions: NavOptions? = null) {
    navigate(DETAILRELATIVEMATCH_ROUTE, navOptions)
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.detailRelativeMatchNavGraph(
    navController: NavController,
    onBackPressedClick: () -> Unit
) {
    composable(route = DETAILRELATIVEMATCH_ROUTE) {
        val sharedViewModel = navController.getBackStackEntry(RELATIVEMATCH_ROUTE).run {
            hiltViewModel<RelativeMatchViewModel>(this)
        }
        DetailRelativeMatchRoute(
            relativeMatchViewModel = sharedViewModel,
            onBackPressedClick = onBackPressedClick
        )
    }
}
