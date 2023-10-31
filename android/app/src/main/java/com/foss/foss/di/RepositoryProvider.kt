package com.foss.foss.di

import com.foss.foss.data.repository.DefaultMatchRepository
import com.foss.foss.data.repository.DefaultUserRepository
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.UserRepository

class RepositoryProvider {

    private val remoteDataSourceProvider = RemoteDataSourceProvider()

    val userRepository: UserRepository =
        DefaultUserRepository(remoteDataSourceProvider.userRemoteDataSource)
    val matchRepository: MatchRepository =
        DefaultMatchRepository(remoteDataSourceProvider.matchRemoteDataSource)
}
