package com.example.teamproject.di

import com.example.teamproject.data.repository.DefaultMatchRepository
import com.example.teamproject.data.repository.DefaultUserRepository
import com.searchingstats.repository.MatchRepository
import com.searchingstats.repository.UserRepository

class RepositoryProvider {

    private val remoteDataSourceProvider = RemoteDataSourceProvider()

    val userRepository: UserRepository =
        DefaultUserRepository(remoteDataSourceProvider.userRemoteDataSource)
    val matchRepository: MatchRepository =
        DefaultMatchRepository(remoteDataSourceProvider.matchRemoteDataSource)
}
