package com.foss.foss.feature.main

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foss.foss.R
import com.foss.foss.feature.matchsearching.recent.NewRecentMatchActivity
import com.foss.foss.util.getPretendFontFamily

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.foss_bk))
            .fillMaxHeight()
    ) {
        MainAppLogo()
        MainSlogan()
        MainEventBanner()
        MainStatSearchingMenu()
    }
}

@Composable
fun MainAppLogo(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .background(colorResource(id = R.color.foss_bk))
            .padding(top = 16.dp, start = 23.dp)
            .width(56.dp)
            .height(28.dp),
        painter = painterResource(id = R.drawable.ic_foss_new_logo),
        contentDescription = "image of foss application"
    )
}

@Composable
fun MainSlogan(modifier: Modifier = Modifier) {
    val navyAndBlack =
        listOf(colorResource(id = R.color.foss_navy), colorResource(id = R.color.black))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(
                top = 18.dp,
                start = 20.dp,
                end = 20.dp
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = navyAndBlack,
                    startY = 0f,
                    endY = 1000f
                ),
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
    ) {
        Text(
            fontSize = 20.sp,
            modifier = Modifier.padding(
                top = 24.dp,
                start = 20.dp
            ),
            lineHeight = 30.sp,
            fontFamily = getPretendFontFamily(),
            fontWeight = FontWeight.Bold,
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(color = colorResource(id = R.color.foss_green))
                ) {
                    append(stringResource(id = R.string.app_name))
                }
                append(stringResource(R.string.main_slogan_from))
                withStyle(
                    SpanStyle(color = colorResource(id = R.color.foss_green))
                ) {
                    append(stringResource(R.string.main_slogan_fc_online))
                }
                append(stringResource(R.string.main_slogan_search_stats))
            },
            color = colorResource(id = R.color.foss_wt)
        )
        Image(
            modifier = modifier
                .padding(top = 50.dp)
                .width(180.dp)
                .height(180.dp)
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
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 24.dp
            ),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.ic_example_event_banner),
        contentDescription = "image of event banner"
    )
}

@Composable
fun MainStatSearchingMenu(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Text(
        modifier = modifier.padding(
            top = 18.dp,
            start = 25.dp
        ),
        fontFamily = getPretendFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = colorResource(id = R.color.foss_wt),
        text = stringResource(R.string.main_menu_search)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MainNavigationButton(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 20.dp,
                    top = 6.dp,
                    bottom = 26.dp,
                    end = 8.dp
                ),
            symbolImage = painterResource(id = R.drawable.ic_for_recent_match),
            menuDescription = stringResource(id = R.string.common_recent_matches)
        ) {
            context.startActivity(Intent(context, NewRecentMatchActivity::class.java))
        }
        MainNavigationButton(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 8.dp,
                    end = 20.dp,
                    top = 6.dp,
                    bottom = 26.dp
                ),
            symbolImage = painterResource(id = R.drawable.ic_for_relative_match),
            menuDescription = stringResource(id = R.string.common_relative_matches)
        ) {
            context.startActivity(Intent(context, NewRecentMatchActivity::class.java))
        }
    }
}

@Composable
fun MainNavigationButton(
    modifier: Modifier,
    symbolImage: Painter,
    menuDescription: String,
    /**
     * todo : 화면 이동 로직
     */
    navigate: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(
                color = colorResource(id = R.color.foss_gray900),
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
            .clickable { navigate() }
    ) {
        Text(
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 14.dp, start = 12.dp),
            fontFamily = getPretendFontFamily(),
            fontWeight = FontWeight.Bold,
            text = menuDescription,
            color = colorResource(id = R.color.foss_wt)
        )
        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(120.dp)
                .height(140.dp)
                .align(Alignment.BottomEnd),
            painter = symbolImage,
            contentDescription = "image of recent match"
        )
        Image(
            painter = painterResource(id = R.drawable.ic_navigate_button),
            "contentDescription",
            modifier = Modifier
                .size(20.dp)
                .offset(
                    x = 16.dp,
                    y = 48.dp
                )
        )
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun DefaultPreview() {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.foss_bk))
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        MainAppLogo()
        MainSlogan()
        MainEventBanner()
        MainStatSearchingMenu()
    }
}
