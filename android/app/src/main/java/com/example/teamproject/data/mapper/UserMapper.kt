package com.example.teamproject.data.mapper

import com.example.teamproject.data.entity.response.MaxRankResponse
import com.example.teamproject.data.entity.response.UserResponse
import com.searchingstats.model.Rank
import com.searchingstats.model.User

class UserMapper(
    private val matchMapper: MatchMapper
) {

    fun mapToDomain(user: UserResponse) = User(
        accessId = user.accessId,
        nickname = user.nickname,
        level = user.level
    )

    fun mapToDomain(maxRank: MaxRankResponse) = maxRank.ranks.map {
        Rank(
            matchType = matchMapper.mapToDomain(it.matchType),
            division = it.division,
            achievementDate = it.achievementDate
        )
    }
}
