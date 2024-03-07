package com.foss.foss.feature.matchsearching.recent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foss.foss.R
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.WinDrawLoseUiModel
import com.foss.foss.util.MockData.recentMatch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class NewRecentMatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val types = MatchTypeUiModel.values().toList()
            var selected by remember { mutableStateOf(types.first()) }
            var userName by remember { mutableStateOf("") }

            RecentMatchScreen(
                onBackPressedClick = { },
                onRefreshClick = { },
            ) {
                Column(modifier = it.background(colorResource(id = R.color.foss_bk))) {
                    SearchBar(
                        value = userName,
                        onValueChange = { userName = it },
                    )
                    MatchTypeSpinner(
                        selected = selected,
                        list = types,
                        onSelectionChanged = { selected = it },
                        modifier = Modifier.padding(top = 18.dp, bottom = 6.dp, end = 18.dp),
                    )
                    MatchCardColumn(
                        recentMatch.filter { match ->
                            selected == MatchTypeUiModel.ALL || match.matchType == selected
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentMatchScreen(
    onBackPressedClick: () -> Unit,
    onRefreshClick: () -> Unit,
    content: @Composable (modifier: Modifier) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.common_recent_matches),
                        color = colorResource(id = R.color.foss_wt),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressedClick) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = colorResource(R.color.foss_wt),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onRefreshClick) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = colorResource(R.color.foss_wt),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.foss_bk)),
            )
        },
    ) {
        content(modifier = Modifier.padding(it))
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedLeadingIconColor = colorResource(id = R.color.foss_wt),
            unfocusedLeadingIconColor = colorResource(id = R.color.foss_wt),
            focusedContainerColor = colorResource(id = R.color.foss_gray800),
            unfocusedContainerColor = colorResource(id = R.color.foss_gray800),
            focusedPlaceholderColor = colorResource(id = R.color.foss_gray100),
            unfocusedPlaceholderColor = colorResource(id = R.color.foss_wt),
            focusedTextColor = colorResource(id = R.color.foss_wt),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = { Text(text = stringResource(id = R.string.common_request_searching_nickname)) },
        modifier = modifier.searchBarModifier(),
    )
}

@Composable
private fun Modifier.searchBarModifier(): Modifier {
    return this.fillMaxWidth()
        .padding(horizontal = 20.dp)
        .heightIn(44.dp)
        .background(
            color = colorResource(id = R.color.foss_gray800),
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        )
}

@Composable
fun MatchCardColumn(
    data: List<MatchUiModel>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(data) { matchUiModel ->
            MatchCard(
                matchUiModel = matchUiModel,
                matchMvp = R.drawable.ic_player_example,
                opponentTier = R.drawable.twenty,
            )
        }
    }
}

@Composable
fun MatchResult(
    winDrawLoseUiModel: WinDrawLoseUiModel,
    modifier: Modifier = Modifier,
) {
    val color: Color = when (winDrawLoseUiModel) {
        WinDrawLoseUiModel.WIN -> colorResource(id = R.color.foss_blue)
        WinDrawLoseUiModel.LOSE -> colorResource(id = R.color.foss_red)
        WinDrawLoseUiModel.DRAW -> colorResource(id = R.color.foss_gray800)
    }

    val result: String = when (winDrawLoseUiModel) {
        WinDrawLoseUiModel.WIN -> stringResource(id = R.string.recent_match_win)
        WinDrawLoseUiModel.LOSE -> stringResource(id = R.string.recent_match_lose)
        WinDrawLoseUiModel.DRAW -> stringResource(id = R.string.recent_match_draw)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(
                color = color,
                shape = RoundedCornerShape(
                    topStart = CornerSize(5.dp),
                    topEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(5.dp),
                    bottomEnd = CornerSize(0.dp),
                ),
            ),
    ) {
        Text(
            text = result,
            color = colorResource(id = R.color.foss_wt),
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 18.dp),
        )
        Divider(
            thickness = 1.dp,
            color = colorResource(id = R.color.foss_wt),
            modifier = Modifier
                .padding(top = 4.dp)
                .width(12.dp),
        )
        Text(
            // todo : 8분동안 진행했다는 데이터가 넘어오면 수정 필요
            text = "08:00",
            color = colorResource(id = R.color.foss_wt),
            fontSize = 8.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp, start = 6.dp, end = 6.dp),
        )
    }
}

@Composable
fun MatchCard(
    matchUiModel: MatchUiModel,
    matchMvp: Int,
    opponentTier: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .background(
                color = colorResource(id = R.color.foss_gray900),
                shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            ),
    ) {
        MatchResult(WinDrawLose.make(point = matchUiModel.point, otherPoint = matchUiModel.otherPoint).toUiModel())
        MatchMvp(image = matchMvp)
        MatchOpponent(
            opponentName = matchUiModel.opponentName,
            opponentTier = opponentTier,
            point = matchUiModel.point,
            otherPoint = matchUiModel.otherPoint,
        )
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp),
        ) {
            MatchType(
                matchType = matchUiModel.matchType,
                matchTime = matchUiModel.date,
            )
        }
    }
}

@Composable
fun MatchMvp(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        modifier = modifier
            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 15.dp),
    )
}

@Composable
fun MatchOpponent(
    opponentName: String,
    @DrawableRes opponentTier: Int,
    point: Int,
    otherPoint: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.recent_match_opponent),
            fontSize = 12.sp,
            color = colorResource(id = R.color.foss_gray100),
        )
        Spacer(modifier = modifier.padding(vertical = 2.dp))
        Row {
            Image(
                painter = painterResource(id = opponentTier),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp),
            )
            Spacer(modifier = modifier.padding(horizontal = 1.dp))
            Text(
                text = opponentName,
                maxLines = 1,
                fontSize = 12.sp,
                color = colorResource(id = R.color.foss_wt),
            )
        }
        Spacer(modifier = modifier.padding(vertical = 2.dp))
        Row {
            Text(
                text = stringResource(id = R.string.recent_match_match_score),
                fontSize = 10.sp,
                color = colorResource(id = R.color.foss_gray100),

            )
            Spacer(modifier = modifier.padding(horizontal = 2.dp))
            Text(
                text = "$point : $otherPoint",
                fontSize = 10.sp,
                color = colorResource(id = R.color.foss_wt),

            )
        }
    }
}

@Composable
fun MatchType(
    matchType: MatchTypeUiModel,
    matchTime: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End,
        modifier = modifier,
    ) {
        Text(
            text = when (matchType) {
                MatchTypeUiModel.OFFICIAL -> stringResource(id = R.string.common_official_description)
                MatchTypeUiModel.CLASSIC_ONE_TO_ONE -> stringResource(id = R.string.common_classic_one_to_one)
                else -> stringResource(id = R.string.common_all)
            },
            fontSize = 12.sp,
            color = colorResource(id = R.color.foss_wt),

        )
        Text(
            text = matchTime.toTimeDiff(),
            fontSize = 10.sp,
            color = colorResource(id = R.color.foss_gray100),
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
    list: List<MatchTypeUiModel>,
    onSelectionChanged: (selection: MatchTypeUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier.fillMaxWidth(),
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(colorResource(R.color.foss_gray700)),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            onClick = { expanded = !expanded },
        ) {
            Row(
                modifier = Modifier.padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = selected.resId),
                    fontSize = 12.sp,
                )
                Icon(
                    tint = colorResource(id = R.color.foss_wt),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(colorResource(id = R.color.foss_gray700)),
            ) {
                list.forEach { entry ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onSelectionChanged(entry)
                        },
                        text = {
                            Text(
                                text = stringResource(id = entry.resId),
                                color = colorResource(R.color.foss_wt),
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.Start),
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.foss_gray700)),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecentMatchScreenPreview() {
    val types = MatchTypeUiModel.values().toList()
    var selected by remember { mutableStateOf(types.first()) }
    var userName by remember { mutableStateOf("") }
    RecentMatchScreen(
        onBackPressedClick = { },
        onRefreshClick = { },
    ) {
        Column(modifier = it.background(colorResource(id = R.color.foss_bk))) {
            SearchBar(
                value = userName,
                onValueChange = { userName = it },
            )
            MatchTypeSpinner(
                selected = selected,
                list = types,
                onSelectionChanged = {
                    selected = it
                },
                modifier = Modifier.padding(top = 18.dp, bottom = 6.dp, end = 18.dp),
            )
            MatchCardColumn(
                recentMatch.filter { match ->
                    selected == MatchTypeUiModel.ALL || match.matchType == selected
                },
            )
        }
    }
}
