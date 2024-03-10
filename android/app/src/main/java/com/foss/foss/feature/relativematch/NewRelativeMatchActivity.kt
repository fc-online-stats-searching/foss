package com.foss.foss.feature.relativematch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.foss.foss.R
import com.foss.foss.util.MockRelativeMatchData

class NewRelativeMatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "match_list_screen") {
                composable(route = "match_list_screen") {
                    NewRelativeMatchScreen { modifier ->
                        Column(
                            modifier = modifier.background(colorResource(id = R.color.foss_bk))
                        ) {
                            SearchBar()
                            Surface(color = colorResource(id = R.color.foss_bk)) {
                                MatchListScreen(
                                    matches = MockRelativeMatchData.dummyMatches,
                                    onDetailClick = { clickedMatch ->
                                        navController.navigate("destination")
                                    }
                                )
                            }
                        }
                    }
                }
                composable(route = "destination") {
                    // TODO::목적지 설정
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewRelativeMatchScreenPreview() {
    Column(
        modifier = Modifier.background(colorResource(id = R.color.foss_bk))
    ) {
        SearchBar()
        Surface(color = colorResource(id = R.color.foss_bk)) {
            MatchListScreen(
                matches = MockRelativeMatchData.dummyMatches,
                onDetailClick = { }
            )
        }
    }
}
