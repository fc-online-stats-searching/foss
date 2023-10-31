package com.example.teamproject.model.mapper

import com.example.teamproject.model.UserUiModel
import com.searchingstats.model.User

object UserMapper {

    fun User.toUiModel() = UserUiModel(
        nickname = nickname,
        level = level
    )
}
