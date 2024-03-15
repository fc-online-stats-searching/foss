package com.foss.foss.design.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.foss.foss.R
import com.foss.foss.design.FossTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackPressedClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                style = FossTheme.typography.title01,
                color = FossTheme.colors.fossWt,
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressedClick) {
                Icon(
                    modifier = Modifier
                        .width(24.dp)
                        .height(22.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    tint = FossTheme.colors.fossWt,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = onRefreshClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_refresh),
                    tint = FossTheme.colors.fossWt,
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = FossTheme.colors.fossBk)
    )
}
