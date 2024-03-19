package com.foss.foss.data.repository.module

import com.foss.foss.data.repository.DefaultMatchRepository
import com.foss.foss.data.repository.DefaultRelativeMatchRepository
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.RelativeMatchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsMatchRepository(matchRepository: DefaultMatchRepository): MatchRepository

    @Binds
    @Singleton
    fun bindsRelativeMatchRepository(relativeMatchRepository: DefaultRelativeMatchRepository): RelativeMatchRepository
}
