package com.foss.foss.di.manual

import com.foss.foss.data.legacy.repository.DefaultMatchRepository
import com.foss.foss.data.legacy.repository.DefaultUserRepository
import com.foss.foss.repository.legacy.MatchRepository
import com.foss.foss.repository.legacy.UserRepository

class RepositoryProvider {

    private val remoteDataSourceProvider = RemoteDataSourceProvider()

    val userRepository: UserRepository =
        DefaultUserRepository(remoteDataSourceProvider.userRemoteDataSource)
    val matchRepository: MatchRepository =
        DefaultMatchRepository(remoteDataSourceProvider.matchRemoteDataSource)
}
