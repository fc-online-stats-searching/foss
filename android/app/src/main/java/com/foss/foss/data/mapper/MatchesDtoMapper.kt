package com.foss.foss.data.mapper

import com.foss.foss.data.dto.MatchDto
import com.foss.foss.data.dto.MatchesDto
import com.foss.foss.model.Match
import com.foss.foss.model.Score

object MatchesDtoMapper {

    private val commonDtoMapper = CommonDtoMapper()

    fun MatchesDto.toDomainModel(): List<Match> {
        return matchResponse.map { it.toDomainModel(memberInfo.nickname) }
    }

    fun MatchDto.toDomainModel(nickname: String): Match {
        return Match(
            date = commonDtoMapper.mapToTime(timestamp),
            manOfTheMatch = commonDtoMapper.mapToManOfTheMatch(matchDetail.squads),
            matchType = commonDtoMapper.mapToMatchType(matchType),
            opponentName = opponentNickname,
            winDrawLose = commonDtoMapper.mapToWinDrawLose(result),
            score = Score(goals[nickname]!!, goals[opponentNickname]!!)
        )
    }
}
