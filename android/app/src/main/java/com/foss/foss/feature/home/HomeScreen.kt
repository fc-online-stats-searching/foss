package com.foss.foss.feature.home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.foss.foss.R
import com.foss.foss.design.FossTheme

@Composable
fun HomeScreen(
    onShowSnackBar: (message: String) -> Unit = {},
    onRelativeStatButtonClick: () -> Unit = {},
    onRecentStatButtonClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(FossTheme.colors.fossBk)
            .fillMaxHeight()
    ) {
        HomeFossLogo()
        HomeFossSlogan()
        HomeEventBanner()
        HomeStatSearchingMenu(
            onRecentStatButtonClick = onRecentStatButtonClick,
            onRelativeStatButtonClick = onRelativeStatButtonClick
        )
    }
}

@Composable
fun HomeFossLogo(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .background(FossTheme.colors.fossBk)
            .padding(top = 16.dp, start = 23.dp)
            .width(56.dp)
            .height(28.dp),
        painter = painterResource(id = R.drawable.ic_foss_logo),
        contentDescription = "image of foss application"
    )
}

@Composable
fun HomeFossSlogan(modifier: Modifier = Modifier) {
    val navyAndBlack =
        listOf(
            FossTheme.colors.fossNavy,
            FossTheme.colors.fossBk
        )

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
                    endY = 400f
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
            style = FossTheme.typography.title01,
            modifier = Modifier.padding(
                top = 24.dp,
                start = 20.dp
            ),
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(color = FossTheme.colors.fossGreen)
                ) {
                    append(stringResource(id = R.string.app_name))
                }
                append(stringResource(R.string.main_slogan_from))
                withStyle(
                    SpanStyle(color = FossTheme.colors.fossGreen)
                ) {
                    append(stringResource(R.string.main_slogan_fc_online))
                }
                append(stringResource(R.string.main_slogan_search_stats))
            },
            color = FossTheme.colors.fossWt
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
fun HomeEventBanner(modifier: Modifier = Modifier) {
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
fun HomeStatSearchingMenu(
    modifier: Modifier = Modifier,
    onRelativeStatButtonClick: () -> Unit = {},
    onRecentStatButtonClick: () -> Unit = {}
) {
    Text(
        modifier = modifier.padding(
            top = 18.dp,
            start = 25.dp
        ),
        style = FossTheme.typography.title02,
        color = FossTheme.colors.fossWt,
        text = stringResource(R.string.main_menu_search)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeNavigationButton(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = FossTheme.padding.BasicHorizontalPadding,
                    top = 6.dp,
                    bottom = 26.dp,
                    end = FossTheme.padding.BasicHorizontalPadding / 2
                ),
            symbolImage = painterResource(id = R.drawable.ic_for_relative_match),
            description = stringResource(id = R.string.common_relative_matches),
            navigate = onRelativeStatButtonClick
        )
        HomeNavigationButton(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = FossTheme.padding.BasicHorizontalPadding / 2,
                    end = FossTheme.padding.BasicHorizontalPadding,
                    top = 6.dp,
                    bottom = 26.dp
                ),
            symbolImage = painterResource(id = R.drawable.ic_for_recent_match),
            description = stringResource(id = R.string.common_recent_matches),
            navigate = onRecentStatButtonClick
        )
    }
}

@Composable
fun HomeNavigationButton(
    modifier: Modifier,
    symbolImage: Painter,
    description: String,
    navigate: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = FossTheme.colors.fossGray900,
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
            .clickable { navigate() }
    ) {
        val (text, arrow, symbol) = createRefs()
        Text(
            style = FossTheme.typography.title01,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(
                    anchor = parent.top,
                    margin = 14.dp
                )
                start.linkTo(
                    anchor = parent.start,
                    margin = 16.dp
                )
            },
            text = description,
            color = FossTheme.colors.fossWt
        )
        Image(
            painter = painterResource(id = R.drawable.ic_navigate_button),
            "contentDescription",
            modifier = Modifier.constrainAs(arrow) {
                top.linkTo(
                    anchor = text.bottom,
                    margin = 8.dp
                )
                start.linkTo(
                    anchor = parent.start,
                    margin = 14.dp
                )
            }
        )
        Image(
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(120.dp)
                .height(140.dp)
                .constrainAs(symbol) {
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = parent.bottom)
                    top.linkTo(
                        anchor = parent.top,
                        margin = 40.dp
                    )
                },
            painter = symbolImage,
            contentDescription = "image of recent match"
        )
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun HomePreview() {
    HomeScreen()
}
