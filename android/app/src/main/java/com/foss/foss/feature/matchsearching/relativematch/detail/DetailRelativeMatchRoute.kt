package com.foss.foss.feature.matchsearching.relativematch.detail

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foss.foss.design.FossTheme
import com.foss.foss.design.component.FossTopBar
import com.foss.foss.feature.matchsearching.recentmatch.MatchTypeSpinner
import com.foss.foss.feature.matchsearching.recentmatch.MatchesItem
import com.foss.foss.feature.matchsearching.relativematch.RelativeMatchUiState
import com.foss.foss.feature.matchsearching.relativematch.RelativeMatchViewModel
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchesUiModel

@Composable
fun DetailRelativeMatchRoute(
    relativeMatchViewModel: RelativeMatchViewModel,
    onBackPressedClick: () -> Unit = {}
) {
    /**
     * todo: 상세화면에서는 matchType 필요없음
     */
    var selectedMatchType by remember { mutableStateOf(MatchTypeUiModel.entries.first()) }
    val uiState by relativeMatchViewModel.uiState.collectAsStateWithLifecycle()

    DetailRelativeMatchScreen(
        uiState = uiState,
        onBackPressedClick = onBackPressedClick,
        onSelectionChanged = { selectedMatchType = it },
        selectedMatchType = selectedMatchType
    )
}

@Composable
fun DetailRelativeMatchScreen(
    uiState: RelativeMatchUiState,
    onBackPressedClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onSelectionChanged: (MatchTypeUiModel) -> Unit = {},
    selectedMatchType: MatchTypeUiModel
) {
    when (uiState) {
        is RelativeMatchUiState.RelativeMatches -> {
            Scaffold(
                topBar = {
                    FossTopBar(
                        title = uiState.opponentName,
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
                    DetailMatchColumn(matchesUiModel = uiState.showingMatchDetails)
                }
            }
        }

        else -> onBackPressedClick()
    }
}

@Composable
fun DetailMatchColumn(
    matchesUiModel: List<MatchesUiModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        matchesUiModel.forEach { matchesUiModel ->
            MatchesItem(matches = matchesUiModel)
        }
    }
}
