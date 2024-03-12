package com.foss.foss.feature.matchsearching.recentmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.foss.foss.design.FossTheme

class RecentMatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FossTheme{
                RecentMatchScreen()
            }
        }
    }
}

