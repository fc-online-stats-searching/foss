package com.foss.foss.design.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.foss.foss.R
import com.foss.foss.design.FossTheme

@Composable
fun EmptyMatchText(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.ic_ball),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.common_empty_match),
            color = FossTheme.colors.fossWt,
            style = FossTheme.typography.body01
        )
    }
}

@Preview
@Composable
fun EmptyMatchTextPreview() {
    FossTheme {
        EmptyMatchText()
    }
}
