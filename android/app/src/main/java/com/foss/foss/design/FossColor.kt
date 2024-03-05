package com.foss.foss.design

import androidx.compose.ui.graphics.Color

data class FossColor(
    val fossGreen: Color = Color(0xFF07F468),
    val fossRed: Color = Color(0xFFD21613),
    val fossBlue: Color = Color(0xFF2266F9),
    val fossBk: Color = Color(0xFF121214),
    val fossWt: Color = Color(0xFFF9F9FD),
    val fossGray900: Color = Color(0xFF202027),
    val fossGray800: Color = Color(0xFF2C2C34),
    val fossGray700: Color = Color(0xFF42424A),
    val fossGray300: Color = Color(0xFF84838D),
    val fossGray200: Color = Color(0xFF9897A1),
    val fossGray50: Color = Color(0xFFF9F9FD)
)

/**
 * todo: 다크 모드와 라이트 모드를 구분한다.
 */
val lightColor = FossColor()
val darkColor = FossColor()
