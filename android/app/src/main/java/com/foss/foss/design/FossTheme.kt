package com.foss.foss.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalCustomColors =
    staticCompositionLocalOf {
        FossColor()
    }
val LocalCustomTypography =
    staticCompositionLocalOf {
        FossTypography()
    }
val LocalCustomPadding =
    staticCompositionLocalOf {
        FossPadding()
    }

@Composable
fun FossTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
            darkTheme -> darkColor
            else -> lightColor
        }
    CompositionLocalProvider(
        LocalCustomColors provides colorScheme,
        LocalCustomTypography provides FossTypography(),
        LocalCustomPadding provides FossPadding(),
        content = content
    )
}

object FossTheme {
    val colors: FossColor
        @Composable
        get() = LocalCustomColors.current
    val typography: FossTypography
        @Composable
        get() = LocalCustomTypography.current
    val padding: FossPadding
        @Composable
        get() = LocalCustomPadding.current
}
