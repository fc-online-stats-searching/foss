package com.foss.foss.feature.matchsearching.recent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foss.foss.R

class NewRecentMatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatchCard()
        }
    }
}

@Composable
fun MatchResult(
    modifier: Modifier = Modifier,
    color: Int,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(
                color = colorResource(id = color),
                shape = RoundedCornerShape(
                    topStart = CornerSize(5.dp),
                    topEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(5.dp),
                    bottomEnd = CornerSize(0.dp),
                ),
            ),
    ) {
        Text(
            text = "승",
            color = colorResource(id = R.color.foss_wt),
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 18.dp),
        )
        Text(
            text = "----",
            color = colorResource(id = R.color.foss_wt),
            modifier = Modifier.padding(top = 4.dp),
        )
        Text(
            text = "08:00",
            color = colorResource(id = R.color.foss_wt),
            fontSize = 8.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp, start = 6.dp, end = 6.dp),
        )
    }
}

@Composable
fun MatchCard(modifier: Modifier = Modifier) {
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
        MatchResult(color = R.color.foss_blue)
        MatchMvp(image = R.drawable.ic_player_example)
        MatchOpponent()
        // MatchType을 포함하는 Column
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp), // 오른쪽 패딩 추가
        ) {
            MatchType()
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
        modifier = Modifier
            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 15.dp),
    )
}

@Composable
fun MatchOpponent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "상대 구단주",
            fontSize = 12.sp,
            color = colorResource(id = R.color.foss_gray100),
        )
        Spacer(modifier = modifier.padding(vertical = 2.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.twenty),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp),
            )
            Spacer(modifier = modifier.padding(horizontal = 1.dp))
            Text(
                text = "신공학관캣맘",
                maxLines = 1,
                fontSize = 12.sp,
                color = colorResource(id = R.color.foss_wt),
            )
        }
        Spacer(modifier = modifier.padding(vertical = 2.dp))
        Row {
            Text(
                text = "매치 스코어",
                fontSize = 10.sp,
                color = colorResource(id = R.color.foss_gray100),

            )
            Spacer(modifier = modifier.padding(horizontal = 2.dp))
            Text(
                text = "1 : 0",
                fontSize = 10.sp,
                color = colorResource(id = R.color.foss_wt),

            )
        }
    }
}

@Composable
fun MatchType(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            text = "공식경기",
            fontSize = 12.sp,
            color = colorResource(id = R.color.foss_wt),

        )
        Text(
            text = "15분전",
            fontSize = 10.sp,
            color = colorResource(id = R.color.foss_gray100),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MatchType()
}

@Preview(showBackground = true)
@Composable
fun MatchCardPreview() {
    MatchCard()
}
