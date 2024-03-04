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
                onRelativeStatButtonClick = {},
                onRecentStatButtonClick = {},
                onShowSnackBar = onShowSnackBar
            )
            /**
             * todo: relativeNavGraph
             */

            /**
             * todo: recentNavGraph
             */
        }
        padding
    }
}
