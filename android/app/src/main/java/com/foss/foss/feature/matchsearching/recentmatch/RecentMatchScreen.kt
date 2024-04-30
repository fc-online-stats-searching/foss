package com.foss.foss.feature.matchsearching.recentmatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foss.foss.R
import com.foss.foss.design.FossTheme
import com.foss.foss.design.component.FossTopBar
import com.foss.foss.design.component.NicknameSearchingTextField
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.util.MockData

@Composable
fun RecentMatchRoute(
    onBackPressedClick: () -> Unit,
    onShowSnackBar: (message: String) -> Unit,
    recentMatchViewModel: RecentMatchViewModel = hiltViewModel()
) {
    val uiState by recentMatchViewModel.uiState.collectAsStateWithLifecycle()
    var userName by remember { mutableStateOf("") }
    var selectedMatchType by remember { mutableStateOf(MatchTypeUiModel.entries.first()) }
    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        recentMatchViewModel.event.collect { uiEvent ->
            when (uiEvent) {
                is RecentMatchEvent.RefreshFailed -> {
                    onShowSnackBar(context.getString(R.string.common_refresh_failed_message))
                }

                is RecentMatchEvent.RefreshSucceed -> {
                    onShowSnackBar(context.getString(R.string.common_refresh_succeed_message))
                }

                else -> {}
            }
        }
    }
    RecentMatchScreen(
        onBackPressedClick = onBackPressedClick,
        onRefreshClick = { recentMatchViewModel.refreshMatches(userName) },
        onSearch = { recentMatchViewModel.fetchMatches(userName, selectedMatchType) },
        onValueChange = { userName = it },
        onSelectionChanged = { selectedMatchType = it },
        uiState = uiState,
        userName = userName,
        selectedMatchType = selectedMatchType
    )
}

@Composable
fun RecentMatchScreen(
    onBackPressedClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onSearch: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onSelectionChanged: (MatchTypeUiModel) -> Unit = {},
    uiState: RecentMatchUiState,
    userName: String,
    selectedMatchType: MatchTypeUiModel
) {
    var isFocused by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            FossTopBar(
                title = stringResource(id = R.string.common_recent_matches),
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
            NicknameSearchingTextField(
                value = userName,
                onValueChange = { searchingName ->
                    onValueChange(searchingName)
                },
                onSearch = onSearch,
                isFocused = isFocused,
                modifier = Modifier.onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
            )
            MatchTypeSpinner(
                selected = selectedMatchType,
                matchTypes = MatchTypeUiModel.entries,
                onSelectionChanged = onSelectionChanged,
                modifier = Modifier.padding(top = 18.dp, end = 20.dp)
            )
            MatchColumn(uiState = uiState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecentMatchScreenPreview() {
    RecentMatchScreen(
        uiState = RecentMatchUiState.RecentMatch(MockData.recentMatch),
        userName = "",
        selectedMatchType = MatchTypeUiModel.ALL

    )
}

@Preview(showBackground = true)
@Composable
fun RecentMatchScreenLoadingPreview() {
    RecentMatchScreen(
        uiState = RecentMatchUiState.Loading,
        userName = "",
        selectedMatchType = MatchTypeUiModel.ALL
    )
}
