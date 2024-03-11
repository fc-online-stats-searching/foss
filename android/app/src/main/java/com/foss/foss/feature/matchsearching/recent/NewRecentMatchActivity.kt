package com.foss.foss.feature.matchsearching.recent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foss.foss.R
import com.foss.foss.design.FossTheme
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.WinDrawLoseUiModel
import com.foss.foss.model.WinDrawLoseUiModel.Companion.getColorResId
import com.foss.foss.model.WinDrawLoseUiModel.Companion.getStringResId
import com.foss.foss.util.MockData.recentMatch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class NewRecentMatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val types = MatchTypeUiModel.entries
            var selected by remember { mutableStateOf(types.first()) }
            var userName by remember { mutableStateOf("") }
            var isFocused by remember { mutableStateOf(false) }

            RecentMatchScreen(
                onBackPressedClick = { },
                onRefreshClick = { }
            ) { it ->
                Column(modifier = it.background(FossTheme.colors.fossBk)) {
                    NicknameSearchingTextField(
                        modifier = Modifier.onFocusChanged { isFocused = it.isFocused },
                        value = userName,
                        onValueChange = { userName = it },
                        isFocused = isFocused
                    )
                    MatchTypeSpinner(
                        selected = selected,
                        list = types,
                        onSelectionChanged = { selected = it },
                        modifier = Modifier.padding(top = 18.dp, bottom = 6.dp, end = 18.dp)
                    )
                    MatchCardColumn(
                        recentMatch.filter { match ->
                            selected == MatchTypeUiModel.ALL || match.matchType == selected
                        }
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
    content: @Composable (modifier: Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = FossTheme.typography.title01,
                        text = stringResource(id = R.string.common_recent_matches),
                        color = FossTheme.colors.fossWt
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressedClick) {
                        Icon(
                            modifier = Modifier
                                .width(24.dp)
                                .height(22.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null,
                            tint = FossTheme.colors.fossWt
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onRefreshClick) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = FossTheme.colors.fossWt
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FossTheme.colors.fossBk)
            )
        }
    ) {
        content(Modifier.padding(it))
    }
}

@Composable
fun NicknameSearchingTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    isFocused: Boolean = false,
    placeHolderString: String = stringResource(id = R.string.common_request_searching_nickname)
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        cursorBrush = SolidColor(FossTheme.colors.fossWt),
        textStyle = FossTheme.typography.body01.copy(color = FossTheme.colors.fossWt),
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = FossTheme.padding.BasicHorizontalPadding)
                    .height(44.dp)
                    .background(
                        color = FossTheme.colors.fossGray800,
                        shape = RoundedCornerShape(corner = CornerSize(5.dp))
                    )
                    .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier.padding(start = 10.dp),
                    tint = FossTheme.colors.fossGray300,
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(3.dp))
                if (!isFocused && value.isEmpty()) {
                    Text(
                        style = FossTheme.typography.body01,
                        text = placeHolderString,
                        color = FossTheme.colors.fossGray300
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun MatchCardColumn(
    data: List<MatchUiModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(data) { matchUiModel ->
            MatchCard(
                matchUiModel = matchUiModel,
                matchMvp = R.drawable.ic_player_example,
                opponentTier = R.drawable.twenty
            )
        }
    }
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
                color = colorResource(id = winDrawLoseUiModel.getColorResId()),
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
            color = colorResource(id = R.color.foss_wt),
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
fun MatchCard(
    matchUiModel: MatchUiModel,
    matchMvp: Int,
    opponentTier: Int,
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
            WinDrawLose.make(
                point = matchUiModel.point,
                otherPoint = matchUiModel.otherPoint
            ).toUiModel()
        )
        MatchMvp(image = matchMvp)
        MatchOpponent(
            opponentName = matchUiModel.opponentName,
            opponentTier = opponentTier,
            point = matchUiModel.point,
            otherPoint = matchUiModel.otherPoint
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
                matchType = matchUiModel.matchType,
                matchTime = matchUiModel.date
            )
        }
    }
}

@Composable
fun MatchMvp(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int
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
        Image(
            modifier = Modifier
                .width(52.dp)
                .height(52.dp),
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}

@Composable
fun MatchOpponent(
    opponentName: String,
    @DrawableRes opponentTier: Int,
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
                painter = painterResource(id = opponentTier),
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
    list: List<MatchTypeUiModel>,
    onSelectionChanged: (selection: MatchTypeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(FossTheme.colors.fossGray700),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            onClick = { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier.padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = selected.resId),
                    fontSize = 12.sp
                )
                Icon(
                    tint = FossTheme.colors.fossWt,
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(FossTheme.colors.fossGray700)
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
                                color = FossTheme.colors.fossWt,
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.Start)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(FossTheme.colors.fossGray700)
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
        onRefreshClick = { }
    ) {
        Column(modifier = it.background(FossTheme.colors.fossBk)) {
            NicknameSearchingTextField(
                value = userName,
                onValueChange = { userName = it }
            )
            MatchTypeSpinner(
                selected = selected,
                list = types,
                onSelectionChanged = {
                    selected = it
                },
                modifier = Modifier.padding(top = 18.dp, bottom = 6.dp, end = 18.dp)
            )
            MatchCardColumn(
                recentMatch.filter { match ->
                    selected == MatchTypeUiModel.ALL || match.matchType == selected
                }
            )
        }
    }
}
