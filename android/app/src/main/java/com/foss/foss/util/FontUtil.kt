package com.foss.foss.util

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.foss.foss.R

fun getPretendFontFamily() = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_thin, FontWeight.Thin)
)
