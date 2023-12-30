package com.foss.foss.model

data class Nickname(val name: String) {
    init {
        require(name.isNotBlank()) { ERROR_BLACK }
    }

    companion object {
        const val ERROR_BLACK = "닉네임은 공백일 수 없습니다."
    }
}
