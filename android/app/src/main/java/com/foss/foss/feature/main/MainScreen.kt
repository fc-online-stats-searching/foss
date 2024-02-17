package com.foss.foss.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foss.foss.R

@Composable
fun MainScreen() {
    Column(modifier = Modifier.background(colorResource(id = R.color.foss_bk))) {
        MainAppLogo()
        MainSlogan()
        MainEventBanner()
    }
}

@Composable
fun MainAppLogo(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .padding(top = 16.dp, start = 23.dp)
            .wrapContentWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_foss_new_logo),
            contentDescription = "image of foss application",
            modifier = modifier.background(colorResource(id = R.color.foss_bk))
        )
    }
}

@Composable
fun MainSlogan(modifier: Modifier = Modifier) {
    val navyAndBlack =
        listOf(colorResource(id = R.color.foss_navy), colorResource(id = R.color.black))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                top = 18.dp,
                start = 20.dp,
                end = 20.dp
            )
            .clip(
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = navyAndBlack,
                    startY = 0f,
                    endY = 1000f

                )
            )
    ) {
        Text(
            fontSize = 20.sp,
            modifier = modifier.padding(
                top = 24.dp,
                start = 20.dp
            ),
            lineHeight = 28.sp,
            fontFamily = getPretendFontFamily(),
            fontWeight = FontWeight.Bold,
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(color = colorResource(id = R.color.foss_green))
                ) {
                    append("FOSS")
                }
                append(" 에서\n")
                withStyle(
                    SpanStyle(color = colorResource(id = R.color.foss_green))
                ) {
                    append("FC ONLINE")
                }
                append(" 전적을\n검색해보세요!")
            },
            color = Color.White
        )
        Image(
            modifier = modifier
                .padding(top = 50.dp)
                .width(160.dp)
                .height(160.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.ic_joystick),
            contentDescription = "image of slogan"
        )
    }
}

@Composable
fun MainEventBanner(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 24.dp),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.ic_example_event_banner),
        contentDescription = "image of event banner"
    )
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun DefaultPreview() {
    Column(modifier = Modifier.background(colorResource(id = R.color.foss_bk))) {
        MainAppLogo()
        MainSlogan()
        MainEventBanner()
    }
}

fun getPretendFontFamily() = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_thin, FontWeight.Thin)
)
