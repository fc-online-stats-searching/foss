package com.foss.foss.data.legacy.repository

import com.foss.foss.data.legacy.datasource.UserRemoteDataSource
import com.foss.foss.data.legacy.entity.request.MaxRankRequest
import com.foss.foss.data.legacy.entity.request.UserRequest
import com.foss.foss.data.legacy.mapper.MatchMapper
import com.foss.foss.data.legacy.mapper.UserMapper
import com.foss.foss.model.legacy.Rank
import com.foss.foss.model.legacy.User
import com.foss.foss.repository.legacy.UserRepository

class DefaultUserRepository(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    // todo: need dependency injection
    private val userMapper: UserMapper = UserMapper(MatchMapper())

    override suspend fun fetchUser(nickName: String): Result<User> = runCatching {
        userRemoteDataSource
            .fetchUser(UserRequest(nickName))
            .run { userMapper.mapToDomain(this) }
    }

    override suspend fun fetchMaxRank(userAccessId: String): Result<List<Rank>> = runCatching {
        userRemoteDataSource
            .fetchMaxRank(MaxRankRequest(userAccessId))
            .run { userMapper.mapToDomain(this) }
    }
}
