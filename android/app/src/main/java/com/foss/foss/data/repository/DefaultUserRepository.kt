package com.foss.foss.data.repository

import com.foss.foss.data.datasource.UserRemoteDataSource
import com.foss.foss.data.entity.request.MaxRankRequest
import com.foss.foss.data.entity.request.UserRequest
import com.foss.foss.data.mapper.MatchMapper
import com.foss.foss.data.mapper.UserMapper
import com.foss.foss.model.Rank
import com.foss.foss.model.User
import com.foss.foss.repository.UserRepository

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
