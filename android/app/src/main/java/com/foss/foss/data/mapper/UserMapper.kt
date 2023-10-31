package com.foss.foss.data.mapper

import com.foss.foss.data.entity.response.MaxRankResponse
import com.foss.foss.data.entity.response.UserResponse
import com.foss.foss.model.Rank
import com.foss.foss.model.User

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
