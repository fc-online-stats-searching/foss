package com.foss.foss.design

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.foss.foss.R

val PretendardFont = FontFamily(
    Font(
        resId = R.font.pretendard_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.pretendard_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.pretendard_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.pretendard_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.pretendard_thin,
        weight = FontWeight.Thin
    )
)

@Immutable
data class FossTypography(
    val heading: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 33.6.sp
        ),
    val title01: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp
        ),
    val title02: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.4).sp
        ),
    val body01: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 18.sp
        ),
    val body02: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
    val body03: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
    val body04: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
    val body05: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
    val caption01: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            lineHeight = 18.sp
        ),
    val caption02: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 18.sp
        ),
    val caption03: TextStyle =
        TextStyle(
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Medium,
            fontSize = 8.sp,
            lineHeight = 16.sp
        )
)
