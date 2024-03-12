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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.compose.rememberNavController
import com.foss.foss.R
import com.foss.foss.design.FossTheme
import com.foss.foss.design.component.NicknameSearchingTextField
import com.foss.foss.model.RelativeMatchUiModel
import com.foss.foss.util.MockRelativeMatchData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelativeMatchScreen(
    onRelativeMatchClick: (relativeMatch: RelativeMatchUiModel) -> Unit = {}
) {
    var userName by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = FossTheme.typography.title01,
                        color = FossTheme.colors.fossWt,
                        text = stringResource(id = R.string.common_relative_matches)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO : 현재 액티비티를 종료하는 기능*/ }) {
                        Icon(
                            modifier = Modifier
                                .width(24.dp)
                                .height(22.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            tint = FossTheme.colors.fossWt,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO : 전적을 갱신하는 기능*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_refresh),
                            tint = FossTheme.colors.fossWt,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FossTheme.colors.fossBk)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(colorResource(id = R.color.foss_bk))
        ) {
            NicknameSearchingTextField(
                modifier = Modifier.onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
                value = userName,
                onValueChange = { searchingName ->
                    userName = searchingName
                },
                isFocused = isFocused
            )
            Surface(color = colorResource(id = R.color.foss_bk)) {
                RelativeMatchColumn(
                    relativeMatches = MockRelativeMatchData.dummyMatches,
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
    modifier: Modifier = Modifier,
    relativeMatches: List<RelativeMatchUiModel>,
    onRelativeMatchClicked: (RelativeMatchUiModel) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(
                start = FossTheme.padding.BasicHorizontalPadding,
                top = 20.dp,
            ),
            style = FossTheme.typography.body05,
            color = FossTheme.colors.fossGray200,
            text = stringResource(R.string.relative_match_informatinon)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(relativeMatches) { match ->
                RelativeMatchItem(
                    relativeMatch = match,
                    onRelativeMatchClick = onRelativeMatchClicked
                )
            }
        }
    }
}

@Composable
fun RelativeMatchItem(
    relativeMatch: RelativeMatchUiModel,
    onRelativeMatchClick: (RelativeMatchUiModel) -> Unit
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
                painter = tierImgResource,
                contentDescription = "Rank",
                modifier = Modifier
                    .size(60.dp)
            )
            Spacer(Modifier.width(15.dp))
            RelativeMatchOpponentData(
                modifier = Modifier.weight(1f),
                relativeMatch = relativeMatch
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
    modifier: Modifier = Modifier,
    relativeMatch: RelativeMatchUiModel,
    winRate: Float,
    backgroundColor: Color
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
            modifier = modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = FossTheme.typography.body02,
                color = FossTheme.colors.fossWt,
                text = if (relativeMatch.numberOfWins > relativeMatch.numberOfLoses) "승" else "패"
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
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = FossTheme.typography.caption03,
                color = FossTheme.colors.fossWt,
                text = String.format(stringResource(R.string.item_relative_match_win_rate), winRate)
            )
        }
    }
}

@Composable
fun RelativeMatchOpponentData(
    modifier: Modifier = Modifier,
    relativeMatch: RelativeMatchUiModel
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            style = FossTheme.typography.caption02,
            color = FossTheme.colors.fossGray100,
            text = stringResource(R.string.item_relative_match_opponent_nickname)
        )
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            style = FossTheme.typography.body02,
            color = FossTheme.colors.fossWt,
            maxLines = 1,
            text = relativeMatch.opponentName,
        )
        Text(
            style = FossTheme.typography.caption02,
            color = FossTheme.colors.fossGray100,
            text = stringResource(R.string.item_relative_match_minute_after_latest_match),
        )
    }
}

@Composable
fun RelativeMatchTotalResult(
    modifier: Modifier = Modifier,
    relativeMatch: RelativeMatchUiModel,
    onRelativeMatchClick: (relativeMatch: RelativeMatchUiModel) -> Unit
) {
    Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 5.dp)) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            style = FossTheme.typography.body03,
            color = FossTheme.colors.fossWt,
            text = stringResource(R.string.item_relative_match_total_result).format(
                relativeMatch.numberOfGames,
                relativeMatch.numberOfWins,
                relativeMatch.numberOfDraws,
                relativeMatch.numberOfLoses
            )
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            style = FossTheme.typography.caption02,
            color = FossTheme.colors.fossGray100,
            text = stringResource(R.string.item_relative_match_total_gain_loss).format(
                relativeMatch.goal,
                relativeMatch.conceded
            ),
        )
        Icon(
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    onRelativeMatchClick(relativeMatch)
                },
            painter = painterResource(id = R.drawable.ic_arrow_to_right),
            tint = Color.White,
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewRelativeMatchScreenPreview() {
    RelativeMatchScreen()
}
