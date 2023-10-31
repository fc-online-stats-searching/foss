package com.foss.foss.model.mapper

import com.foss.foss.model.User
import com.foss.foss.model.UserUiModel

object UserMapper {

    fun User.toUiModel() = UserUiModel(
        nickname = nickname,
        level = level
    )
}
