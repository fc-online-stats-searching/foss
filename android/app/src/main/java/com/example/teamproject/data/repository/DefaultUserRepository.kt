package com.example.teamproject.data.repository

import com.example.teamproject.data.datasource.UserRemoteDataSource
import com.example.teamproject.data.entity.request.MaxRankRequest
import com.example.teamproject.data.entity.request.UserRequest
import com.example.teamproject.data.mapper.MatchMapper
import com.example.teamproject.data.mapper.UserMapper
import com.searchingstats.model.Rank
import com.searchingstats.model.User
import com.searchingstats.repository.UserRepository

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
