package com.foss.foss.feature.matchsearching.relativematch.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.foss.foss.design.FossTheme
import com.foss.foss.design.component.FossTopBar
import com.foss.foss.feature.matchsearching.recentmatch.MatchTypeSpinner
import com.foss.foss.feature.matchsearching.recentmatch.MatchesItem
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchesUiModel

//@Composable
//fun DetailRelativeMatchRoute(
//    matchesUiModels: List<MatchesUiModel>,
//    onBackPressedClick: () -> Unit
//) {
//    Log.d("detail", "DetailRelativeMatchRoute: ")
//}
@Composable
fun DetailRelativeMatchRoute(
    matchesUiModels: List<MatchesUiModel>,
    onBackPressedClick: () -> Unit
) {
    var selectedMatchType by remember { mutableStateOf(MatchTypeUiModel.entries.first()) }

    Log.d("detail", "DetailRelativeMatchScreen: ")
    DetailRelativeMatchScreen(
        matchesUiModels = matchesUiModels,
        onBackPressedClick = onBackPressedClick,
        onSelectionChanged = {
            if (selectedMatchType != it) {
                selectedMatchType = it
            }
        },
        selectedMatchType = selectedMatchType
    )
}

@Composable
fun DetailRelativeMatchScreen(
    matchesUiModels: List<MatchesUiModel>,
    onBackPressedClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onSelectionChanged: (MatchTypeUiModel) -> Unit = {},
    selectedMatchType: MatchTypeUiModel
) {
    val title =
        if (matchesUiModels.isNotEmpty()) matchesUiModels[0].value[0].opponentName else "뒤로가기"

    Scaffold(
        topBar = {
            FossTopBar(
                title = title,
                onBackPressedClick = onBackPressedClick,
                onRefreshClick = onRefreshClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(FossTheme.colors.fossBk)
        ) {
            MatchTypeSpinner(
                selected = selectedMatchType,
                matchTypes = MatchTypeUiModel.entries,
                onSelectionChanged = onSelectionChanged,
                modifier = Modifier.padding(top = 18.dp, end = 20.dp)
            )
            DetailMatchColumn(matchesUiModels = matchesUiModels)
        }
    }
}

@Composable
fun DetailMatchColumn(
    matchesUiModels: List<MatchesUiModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        matchesUiModels.forEach { matchesUiModel ->
            MatchesItem(matches = matchesUiModel)
        }
    }
}
