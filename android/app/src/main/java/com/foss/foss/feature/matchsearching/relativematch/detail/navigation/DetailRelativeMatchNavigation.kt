package com.foss.foss.feature.matchsearching.relativematch.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.foss.foss.feature.matchsearching.relativematch.detail.DetailRelativeMatchRoute
import com.foss.foss.model.MatchesUiModel
import com.foss.foss.util.LocalDateAdapter
import com.foss.foss.util.LocalDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime

const val DETAILRELATIVEMATCH_ROUTE = "DETAILRELATIVEMATCH"

fun NavController.navigateToDetailRelativeMatch(
    matchesUiModels: List<MatchesUiModel>,
    navOptions: NavOptions? = null
) {
    val matchesUiModelsJson = buildGson().toJson(matchesUiModels)

    navigate("$DETAILRELATIVEMATCH_ROUTE/$matchesUiModelsJson", navOptions)
}

fun NavGraphBuilder.detailRelativeMatchNavGraph(
    onBackPressedClick: () -> Unit
) {
    composable(
        route = "$DETAILRELATIVEMATCH_ROUTE/{matchesUiModels}",
        arguments = listOf(
            navArgument("matchesUiModels") { type = NavType.StringType }
        )
    ) { backStackEntry ->

        backStackEntry?.arguments?.getString("matchesUiModels")?.let { json ->
            val type: Type = object : TypeToken<List<MatchesUiModel>>() {}.type
            val matchesUiModels = buildGson().fromJson<List<MatchesUiModel>>(json, type)

            DetailRelativeMatchRoute(
                matchesUiModels = matchesUiModels,
                onBackPressedClick = onBackPressedClick
            )
        }
    }
}

fun buildGson(): Gson = GsonBuilder()
    .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
    .create()
