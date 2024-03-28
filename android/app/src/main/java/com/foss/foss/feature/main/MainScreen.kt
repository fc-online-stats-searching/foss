package com.foss.foss.feature.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.foss.foss.feature.home.navigation.HOME_ROUTE
import com.foss.foss.feature.home.navigation.homeNavGraph
import com.foss.foss.feature.matchsearching.recentmatch.navigation.navigateToRecentMatch
import com.foss.foss.feature.matchsearching.recentmatch.navigation.recentMatchNavGraph
import com.foss.foss.feature.matchsearching.relativematch.detail.navigation.detailRelativeMatchNavGraph
import com.foss.foss.feature.matchsearching.relativematch.detail.navigation.navigateToDetailRelativeMatch
import com.foss.foss.feature.matchsearching.relativematch.navigation.navigateToRelativeMatch
import com.foss.foss.feature.matchsearching.relativematch.navigation.relativeMatchNavGraph
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navigator: NavHostController = rememberNavController()) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val onShowSnackBar: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        NavHost(
            navController = navigator,
            startDestination = HOME_ROUTE
        ) {
            homeNavGraph(
                onRelativeStatButtonClick = { navigator.navigateToRelativeMatch() },
                onRecentStatButtonClick = { navigator.navigateToRecentMatch() },
                onShowSnackBar = onShowSnackBar
            )

            relativeMatchNavGraph(
                onRelativeMatchClick = { relativeMatch ->
                    navigator.navigateToDetailRelativeMatch(relativeMatch)
                },
                onBackPressedClick = { navigator.popBackStack() },
                onShowSnackBar = onShowSnackBar
            )

            recentMatchNavGraph(
                onBackPressedClick = { navigator.popBackStack() },
                onShowSnackBar = onShowSnackBar
            )

            detailRelativeMatchNavGraph(
                onBackPressedClick = { navigator.popBackStack() }
            )
        }
        padding
    }
}
