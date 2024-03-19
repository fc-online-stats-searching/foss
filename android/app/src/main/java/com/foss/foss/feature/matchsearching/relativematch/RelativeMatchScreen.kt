package com.foss.foss.feature.matchsearching.relativematch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foss.foss.R
import com.foss.foss.design.FossTheme
import com.foss.foss.design.component.EmptyMatchText
import com.foss.foss.design.component.FossTopBar
import com.foss.foss.design.component.NicknameSearchingTextField
import com.foss.foss.model.RelativeMatchUiModel
import com.foss.foss.util.MockRelativeMatchData

@Composable
fun RelativeMatchRoute(
    onRelativeMatchClick: (relativeMatch: RelativeMatchUiModel) -> Unit,
    onBackPressedClick: () -> Unit,
    modifier: Modifier = Modifier,
    relativeMatchViewModel: RelativeMatchViewModel = hiltViewModel()
) {
    val uiState by relativeMatchViewModel.uiState.collectAsStateWithLifecycle()
    var userName by remember { mutableStateOf("") }

    RelativeMatchScreen(
        uiState = uiState,
        userName = userName,
        modifier = modifier,
        onRelativeMatchClick = onRelativeMatchClick,
        onBackPressedClick = onBackPressedClick,
        onRefreshClick = { relativeMatchViewModel.refreshMatches(userName) },
        onSearch = { relativeMatchViewModel.fetchRelativeMatches(userName) },
        onValueChange = { userName = it }
    )
}

@Composable
fun RelativeMatchScreen(
    uiState: RelativeMatchUiState,
    userName: String,
    modifier: Modifier = Modifier,
    onRelativeMatchClick: (relativeMatch: RelativeMatchUiModel) -> Unit = {},
    onBackPressedClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onSearch: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            FossTopBar(
                title = stringResource(id = R.string.common_relative_matches),
                onBackPressedClick = onBackPressedClick,
                onRefreshClick = onRefreshClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(colorResource(id = R.color.foss_bk))
        ) {
            NicknameSearchingTextField(
                value = userName,
                onValueChange = onValueChange,
                isFocused = isFocused,
                onSearch = onSearch,
                modifier = Modifier.onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
            )
            Surface(color = colorResource(id = R.color.foss_bk)) {
                RelativeMatchColumn(
                    uiState = uiState,
                    onRelativeMatchClicked = { relativeMatch ->
                        onRelativeMatchClick(relativeMatch)
                    }
                )
            }
        }
    }
}

@Composable
fun RelativeMatchColumn(
    uiState: RelativeMatchUiState,
    onRelativeMatchClicked: (RelativeMatchUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is RelativeMatchUiState.RelativeMatches -> {
            Column {
                Text(
                    style = FossTheme.typography.body05,
                    color = FossTheme.colors.fossGray200,
                    text = stringResource(R.string.relative_match_informatinon),
                    modifier = Modifier.padding(
                        start = FossTheme.padding.BasicHorizontalPadding,
                        top = 20.dp
                    )
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.relativeMatches) { match ->
                        RelativeMatchItem(
                            relativeMatch = match,
                            onRelativeMatchClick = onRelativeMatchClicked
                        )
                    }
                }
            }
        }
        else -> {
            EmptyMatchText(modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun RelativeMatchItem(
    relativeMatch: RelativeMatchUiModel,
    onRelativeMatchClick: (RelativeMatchUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val winRate = getWinRate(relativeMatch)
    val backgroundColor = getBackgroundColor(relativeMatch)
    val tierImgResource = getTierImageRes(relativeMatch)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = FossTheme.padding.BasicHorizontalPadding,
                top = 14.dp,
                end = FossTheme.padding.BasicHorizontalPadding
            )
            .background(
                FossTheme.colors.fossNavy,
                RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RelativeMatchWinRate(
                relativeMatch = relativeMatch,
                winRate = winRate,
                backgroundColor = backgroundColor
            )
            Spacer(Modifier.width(15.dp))
            Image(
                // TODO : 티어를 받아오는 방법을 의논해야 함
                painter = tierImgResource,
                contentDescription = "Rank",
                modifier = Modifier
                    .size(60.dp)
            )
            Spacer(Modifier.width(15.dp))
            RelativeMatchOpponentData(
                relativeMatch = relativeMatch,
                modifier = Modifier.weight(1f)
            )
            RelativeMatchTotalResult(
                relativeMatch = relativeMatch,
                onRelativeMatchClick = onRelativeMatchClick
            )
            Spacer(Modifier.width(8.dp))
        }
    }
}

fun getWinRate(relativeMatch: RelativeMatchUiModel): Float {
    return if (relativeMatch.numberOfGames > 0) {
        (relativeMatch.numberOfWins.toFloat() / relativeMatch.numberOfGames.toFloat()) * 100
    } else {
        0f
    }
}

@Composable
fun getTierImageRes(relativeMatch: RelativeMatchUiModel): Painter {
    val tierImageRes = if (relativeMatch.opponentName == "똥질긴형") {
        R.drawable.ic_tier_champions
    } else if (relativeMatch.opponentName == "신공학관캣대디") {
        R.drawable.ic_tier_challenger1
    } else if (relativeMatch.opponentName == "신공학관캣맘") {
        R.drawable.ic_tier_pro1
    } else {
        R.drawable.ic_tier_semi2
    }

    return painterResource(id = tierImageRes)
}

@Composable
fun getBackgroundColor(relativeMatch: RelativeMatchUiModel): Color {
    return if (relativeMatch.numberOfWins >= relativeMatch.numberOfLoses) {
        FossTheme.colors.fossBlue
    } else {
        FossTheme.colors.fossRed
    }
}

@Composable
fun RelativeMatchWinRate(
    relativeMatch: RelativeMatchUiModel,
    winRate: Float,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(80.dp)
            .background(
                backgroundColor,
                RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Text(
                style = FossTheme.typography.body02,
                color = FossTheme.colors.fossWt,
                text = if (relativeMatch.numberOfWins > relativeMatch.numberOfLoses) "승" else "패",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                modifier = Modifier
                    .width(15.dp)
                    .height(1.dp)
                    .align(Alignment.CenterHorizontally),
                color = FossTheme.colors.fossWt
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                style = FossTheme.typography.caption03,
                color = FossTheme.colors.fossWt,
                text = String.format(stringResource(R.string.item_relative_match_win_rate), winRate),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun RelativeMatchOpponentData(
    relativeMatch: RelativeMatchUiModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            style = FossTheme.typography.caption02,
            color = FossTheme.colors.fossGray100,
            text = stringResource(R.string.item_relative_match_opponent_nickname),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            style = FossTheme.typography.body02,
            color = FossTheme.colors.fossWt,
            maxLines = 1,
            text = relativeMatch.opponentName,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            style = FossTheme.typography.caption02,
            color = FossTheme.colors.fossGray100,
            text = stringResource(R.string.item_relative_match_minute_after_latest_match)
        )
    }
}

@Composable
fun RelativeMatchTotalResult(
    relativeMatch: RelativeMatchUiModel,
    onRelativeMatchClick: (relativeMatch: RelativeMatchUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 5.dp)) {
        Text(
            style = FossTheme.typography.body03,
            color = FossTheme.colors.fossWt,
            text = stringResource(R.string.item_relative_match_total_result).format(
                relativeMatch.numberOfGames,
                relativeMatch.numberOfWins,
                relativeMatch.numberOfDraws,
                relativeMatch.numberOfLoses
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            style = FossTheme.typography.caption02,
            color = FossTheme.colors.fossGray100,
            text = stringResource(R.string.item_relative_match_total_gain_loss).format(
                relativeMatch.goal,
                relativeMatch.conceded
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_to_right),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    onRelativeMatchClick(relativeMatch)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewRelativeMatchColumnPreview() {
    RelativeMatchColumn(
        uiState = RelativeMatchUiState.RelativeMatches(MockRelativeMatchData.dummyMatches),
        onRelativeMatchClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun NewRelativeMatchScreenPreview() {
    RelativeMatchScreen(
        uiState = RelativeMatchUiState.Default,
        userName = ""
    )
}
