package com.foss.foss.feature.matchsearching.recentmatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foss.foss.R
import com.foss.foss.design.FossProgressBar
import com.foss.foss.design.FossTheme
import com.foss.foss.design.component.EmptyMatchText
import com.foss.foss.design.component.FossTopBar
import com.foss.foss.design.component.NicknameSearchingTextField
import com.foss.foss.model.DivisionUiModel
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.MatchesUiModel
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.WinDrawLoseUiModel
import com.foss.foss.model.WinDrawLoseUiModel.Companion.getStringResId
import com.foss.foss.util.MockData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

@Composable
fun MatchColumn(
    uiState: RecentMatchUiState,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is RecentMatchUiState.RecentMatch -> {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                uiState.matches.forEach { matchesUiModel ->
                    MatchesItem(matches = matchesUiModel)
                }
            }
        }

        is RecentMatchUiState.Loading -> FossProgressBar()

        else -> EmptyMatchText(modifier = modifier.fillMaxSize())
    }
}

fun LazyListScope.MatchesItem(
    matches: MatchesUiModel,
    modifier: Modifier = Modifier
) {
    item {
        MatchDateText(
            date = matches.date,
            modifier = Modifier
        )
    }
    items(matches.value) { matchUiModel ->
        MatchItem(match = matchUiModel)
    }
}

@Composable
fun MatchItem(
    match: MatchUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = FossTheme.padding.BasicHorizontalPadding,
                vertical = 8.dp
            )
            .background(
                color = FossTheme.colors.fossGray900,
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            )
    ) {
        MatchResult(
            winDrawLoseUiModel = WinDrawLose.make(
                point = match.point,
                otherPoint = match.otherPoint
            ).toUiModel()
        )
        MatchMvp(manOfTheMatch = match.manOfTheMatch)
        MatchDetailResult(
            opponentName = match.opponentName,
            // TODO: 티어 받아오기
            opponentDivision = match.opponentDivision,
            point = match.point,
            otherPoint = match.otherPoint
        )
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .align(Alignment.Top)
                .fillMaxHeight()
                .weight(1f)
                .padding(
                    top = 10.dp,
                    end = 12.dp
                )
        ) {
            MatchType(
                matchType = match.matchType,
                matchTime = match.date
            )
        }
    }
}

@Composable
fun MatchDateText(
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    Text(
        style = FossTheme.typography.body02,
        color = FossTheme.colors.fossWt,
        text = date.format(
            DateTimeFormatter.ofPattern(
                stringResource(R.string.item_recent_match_date_format)
            )
        ),
        modifier = modifier.padding(
            start = FossTheme.padding.BasicHorizontalPadding,
            end = FossTheme.padding.BasicHorizontalPadding,
            top = 8.dp
        )
    )
}

@Composable
fun MatchResult(
    winDrawLoseUiModel: WinDrawLoseUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(
                color = getWinDrawLoseColor(winDrawLoseUiModel),
                shape = RoundedCornerShape(
                    topStart = CornerSize(5.dp),
                    topEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(5.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
    ) {
        Text(
            style = FossTheme.typography.body02,
            text = stringResource(id = winDrawLoseUiModel.getStringResId()),
            color = FossTheme.colors.fossWt,
            modifier = Modifier.padding(top = 18.dp)
        )
        Divider(
            thickness = 1.dp,
            color = FossTheme.colors.fossWt,
            modifier = Modifier
                .padding(top = 4.dp)
                .width(12.dp)
        )
        Text(
            style = FossTheme.typography.caption03,
            // todo : 8분동안 진행했다는 데이터가 넘어오면 수정 필요
            text = "08:00",
            color = FossTheme.colors.fossWt,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp, start = 6.dp, end = 6.dp)
        )
    }
}

@Composable
fun MatchMvp(
    manOfTheMatch: Int?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.padding(
            start = 12.dp,
            end = 15.dp
        )
    ) {
        Image(
            alignment = Alignment.TopEnd,
            painter = painterResource(id = R.drawable.ic_mvp),
            contentDescription = null
        )
        AsyncImage(
            modifier = Modifier
                .width(52.dp)
                .height(52.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://fco.dn.nexoncdn.co.kr/live/externalAssets/common/players/p$manOfTheMatch.png")
                .crossfade(true)
                .build(),
            contentDescription = null
        )
    }
}

@Composable
fun MatchDetailResult(
    opponentName: String,
    opponentDivision: DivisionUiModel,
    point: Int,
    otherPoint: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            style = FossTheme.typography.caption02,
            text = stringResource(id = R.string.recent_match_opponent),
            color = FossTheme.colors.fossGray100
        )
        Spacer(modifier = modifier.padding(vertical = 2.dp))
        Row {
            Image(
                painter = painterResource(id = opponentDivision.symbol),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp)
            )
            Spacer(modifier = modifier.padding(horizontal = 1.dp))
            Text(
                style = FossTheme.typography.body02,
                text = opponentName,
                maxLines = 1,
                color = FossTheme.colors.fossWt
            )
        }
        Spacer(modifier = modifier.padding(vertical = 2.dp))
        Row {
            Text(
                text = stringResource(id = R.string.recent_match_match_score),
                style = FossTheme.typography.caption02,
                fontSize = 10.sp,
                color = FossTheme.colors.fossGray100
            )
            Spacer(modifier = modifier.padding(horizontal = 2.dp))
            Text(
                style = FossTheme.typography.caption02,
                text = "$point : $otherPoint",
                fontSize = 10.sp,
                color = FossTheme.colors.fossWt
            )
        }
    }
}

@Composable
fun MatchType(
    matchType: MatchTypeUiModel,
    matchTime: LocalDateTime,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        Text(
            style = FossTheme.typography.body02,
            text = when (matchType) {
                MatchTypeUiModel.OFFICIAL -> stringResource(id = R.string.common_official_description)
                MatchTypeUiModel.CLASSIC_ONE_TO_ONE -> stringResource(id = R.string.common_classic_one_to_one)
                else -> stringResource(id = R.string.common_all)
            },
            fontSize = 12.sp,
            color = FossTheme.colors.fossWt

        )
        Text(
            style = FossTheme.typography.caption02,
            text = matchTime.toTimeDiff(),
            color = FossTheme.colors.fossGray100
        )
    }
}

private fun LocalDateTime.toTimeDiff(): String {
    val hourDiff = ChronoUnit.HOURS.between(this, LocalDateTime.now())
    val minDiff = ChronoUnit.MINUTES.between(this, LocalDateTime.now())
    return when {
        hourDiff > 24 -> "${ChronoUnit.DAYS.between(this, LocalDateTime.now())}일전"
        minDiff >= 60 -> "${hourDiff}시간 전"
        else -> "${minDiff}분 전"
    }
}

@Composable
fun MatchTypeSpinner(
    selected: MatchTypeUiModel,
    matchTypes: List<MatchTypeUiModel>,
    onSelectionChanged: (selection: MatchTypeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var matchTypesButtonSize by remember { mutableStateOf(Size.Zero) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Box {
            Button(
                colors = ButtonDefaults.buttonColors(FossTheme.colors.fossGray700),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(corner = CornerSize(5.dp)),
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 6.dp)
                    .height(26.dp)
                    .onGloballyPositioned { coordinates ->
                        matchTypesButtonSize = coordinates.size.toSize()
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 21.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = selected.resId),
                        style = FossTheme.typography.body04,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_drop_down_arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 10.dp)
                            .width(14.dp)
                            .height(14.dp)
                    )
                }
            }
            MatchTypeDropDownMenu(
                expanded = expanded,
                onDismiss = { expanded = false },
                matchTypeWidth = with(LocalDensity.current) {
                    matchTypesButtonSize.width.toDp()
                },
                matchTypes = matchTypes,
                onSelectionChanged = onSelectionChanged
            )
        }
    }
}

@Composable
fun MatchTypeDropDownMenu(
    expanded: Boolean,
    onDismiss: (Boolean) -> Unit,
    matchTypeWidth: Dp,
    matchTypes: List<MatchTypeUiModel>,
    onSelectionChanged: (selection: MatchTypeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(surface = FossTheme.colors.fossGray700),
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(5.dp))
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss(false) },
            modifier = modifier
                .width(matchTypeWidth)
        ) {
            matchTypes.forEach { entry ->
                DropdownMenuItem(
                    onClick = {
                        onDismiss(false)
                        onSelectionChanged(entry)
                    },
                    text = {
                        Text(
                            text = stringResource(id = entry.resId),
                            color = FossTheme.colors.fossWt,
                            style = FossTheme.typography.body04,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    },
                    modifier = modifier.height(26.dp)
                )
            }
        }
    }
}

@Composable
fun getWinDrawLoseColor(winDrawLoseUiModel: WinDrawLoseUiModel): Color {
    return when (winDrawLoseUiModel) {
        WinDrawLoseUiModel.WIN -> FossTheme.colors.fossBlue
        WinDrawLoseUiModel.LOSE -> FossTheme.colors.fossRed
        WinDrawLoseUiModel.DRAW -> FossTheme.colors.fossGray700
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
