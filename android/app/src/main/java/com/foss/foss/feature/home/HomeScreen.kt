package com.foss.foss.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.foss.foss.R
import com.foss.foss.model.MatchTypeUiModel

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .background(colorResource(R.color.common_dark_gray))
            .fillMaxHeight()
    ) {
        TopAppLogo()
        NicknameSearchingField()
        MenuBar()
        NavigationView()
    }
}

@Composable
fun TopAppLogo(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.padding(12.dp)) {
        Image(
            modifier = Modifier.background(color = colorResource(R.color.common_dark_gray)),
            alignment = Alignment.CenterStart,
            painter = painterResource(R.drawable.ic_foss_logo),
            contentDescription = "logo of foss application"
        )
    }
}

@Composable
fun NicknameSearchingField(modifier: Modifier = Modifier) {
    /**
     * todo: 인자로
     */
    val textState: MutableState<String> = remember {
        mutableStateOf("")
    }
    Surface(
        color = colorResource(R.color.common_dark_gray)
    ) {
        BasicTextField(
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            value = textState.value,
            /**
             * todo: 람다 함수를 인자로
             */
            onValueChange = { textFieldValue ->
                textState.value = textFieldValue
            },
            cursorBrush = SolidColor(Color.White),
            decorationBox = { innerTextField ->
                Row(
                    modifier = modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .background(
                            color = colorResource(R.color.common_darkest_gray),
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(all = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        tint = colorResource(id = R.color.common_light_gray),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_searching),
                        contentDescription = "searching icon"
                    )
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun MenuBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.common_darkest_gray)),
            onClick = {}
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    tint = colorResource(id = R.color.common_light_gray),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_refresh),
                    contentDescription = "refresh icon"
                )
                Text(
                    color = Color.White,
                    text = " 전적 갱신"
                )
            }
        }
        MatchTypesDropDownMenu()
    }
}

@Composable
fun MatchTypesDropDownMenu(modifier: Modifier = Modifier) {
    var isExpanded: Boolean by remember { mutableStateOf(false) }
    var selectedMatchType: String by remember { mutableStateOf("전체 경기") }
    var matchTypesButtonSize by remember { mutableStateOf(Size.Zero) }

    Column {
        Button(
            colors = ButtonDefaults.buttonColors(colorResource(R.color.common_darkest_gray)),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .background(colorResource(id = R.color.common_dark_gray))
                .onGloballyPositioned { coordinates ->
                    matchTypesButtonSize = coordinates.size.toSize()
                },
            onClick = { isExpanded = !isExpanded }
        ) {
            Row(
                modifier = modifier.padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = selectedMatchType
                )
                Icon(
                    tint = colorResource(id = R.color.common_light_gray),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "refresh icon"
                )
            }
        }

        DropdownMenu(
            modifier = modifier
                .background(colorResource(id = R.color.common_darkest_gray))
                .width(with(LocalDensity.current) { matchTypesButtonSize.width.toDp() }),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            MatchTypeUiModel.values()
                .map { matchTypeUiModel ->
                    stringResource(id = matchTypeUiModel.resId)
                }.map { matchTypeDescription ->
                    DropdownMenuItem(
                        onClick = {
                            selectedMatchType = matchTypeDescription
                            isExpanded = false
                        }
                    ) {
                        Text(
                            modifier = modifier.fillMaxWidth(),
                            fontSize = 13.sp,
                            text = matchTypeDescription,
                            color = Color.White
                        )
                    }
                }
        }
    }
}

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController = rememberNavController()) {
    val items = listOf(
        NavigationItem.RecentMatch,
        NavigationItem.RelativeMatch
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Red
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { navigationItem ->
            BottomNavigationItem(
                selected = currentRoute == navigationItem.route,
                onClick = {
                    navController.navigate(navigationItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = navigationItem.icon),
                        contentDescription = "",
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(navigationItem.description) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.RelativeMatch.route) {
        composable(NavigationItem.RecentMatch.route) { RecentScreen() }
        composable(NavigationItem.RelativeMatch.route) { RelativeScreen() }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun DefaultPreview() {
    Column(
        modifier = Modifier.background(colorResource(R.color.common_dark_gray))
    ) {
        TopAppLogo()
        NicknameSearchingField()
        MenuBar()
        NavigationView()
    }
}
