package com.foss.foss.feature.relativematch

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foss.foss.R
import com.foss.foss.model.RelativeMatchUiModel

@Composable
fun MatchListScreen(
    matches: List<RelativeMatchUiModel>,
    onDetailClick: (RelativeMatchUiModel) -> Unit
) {
    Column {
        Text(
            text = "최근 7경기를 함께한 플레이어 입니다",
            fontSize = 12.sp,
            color = Color(0xFF9897A1),
            modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(matches) { match ->
                MatchListItem(
                    match = match,
                    onDetailClick = onDetailClick
                )
            }
        }
    }
}

@Composable
fun MatchListItem(
    match: RelativeMatchUiModel,
    onDetailClick: (RelativeMatchUiModel) -> Unit
) {
    val winRate = if (match.numberOfGames > 0) {
        (match.numberOfWins.toFloat() / match.numberOfGames.toFloat()) * 100
    } else {
        0f
    }

    val backgroundColor = if (match.numberOfWins >= match.numberOfLoses) {
        colorResource(R.color.item_relative_match_win)
    } else {
        colorResource(R.color.foss_red)
    }

    val tier = if (match.opponentName == "똥질긴형") {
        painterResource(R.drawable.tierchallenger)
    } else if (match.opponentName == "신공학관캣대디") {
        painterResource(R.drawable.tierworldclass)
    } else if (match.opponentName == "신공학관캣맘") {
        painterResource(R.drawable.tierpro)
    } else {
        painterResource(R.drawable.tiersemi)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 15.dp, end = 20.dp)
            .background(
                colorResource(R.color.foss_navy),
                RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (match.numberOfWins > match.numberOfLoses) "승" else "패",
                        color = colorResource(R.color.item_relative_match_white_color),
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(
                        color = colorResource(R.color.item_relative_match_white_color),
                        modifier = Modifier
                            .width(15.dp)
                            .height(1.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${String.format("%.0f%%", winRate)}",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        color = colorResource(R.color.item_relative_match_white_color),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

            Spacer(Modifier.width(15.dp))

            Image(
                painter = tier,
                contentDescription = "Rank",
                modifier = Modifier
                    .size(60.dp)
            )

            Spacer(Modifier.width(15.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "구단주명",
                    color = colorResource(R.color.foss_gray100),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = match.opponentName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    color = colorResource(R.color.item_relative_match_white_color),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "최근 경기 15분 전",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = colorResource(R.color.foss_gray100)
                )
            }

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 5.dp)) {
                Text(
                    text = "${match.numberOfGames}전 ${match.numberOfWins}승 ${match.numberOfLoses}패",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.foss_gray50),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "득 ${match.goal} / 실 ${match.conceded}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = colorResource(R.color.foss_gray100),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Details",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onDetailClick(match)
                        }
                )
            }

            Spacer(Modifier.width(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRelativeMatchScreen(
    content: @Composable (modifier: Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.common_relative_matches),
                        color = colorResource(id = R.color.foss_wt)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO : 현재 액티비티를 종료하는 기능*/ }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = colorResource(R.color.foss_wt)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO : 전적을 갱신하는 기능*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.refresh),
                            contentDescription = null,
                            tint = colorResource(R.color.foss_wt)
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.foss_bk))
            )
        }
    ) {
        content(modifier = Modifier.padding(it))
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    TextField(
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
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
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.common_request_searching_nickname),
                color = if (text.isEmpty()) {
                    Color.Gray
                } else {
                    LocalContentColor.current
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .heightIn(44.dp)
            .background(
                color = colorResource(id = R.color.foss_gray800),
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            )
    )
}
