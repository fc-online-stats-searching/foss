package com.example.teamproject.model

data class MatchResultUiModel(
    val matchType: String,
    val otherSideNickname: String,
    val winDrawLose: String,
    val point: Int,
    val otherPoint: Int
)
