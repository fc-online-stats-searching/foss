package com.example.teamproject.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.teamproject.feature.statsearching.StatsViewModel

object ViewModelFactoryProvider {

    private val repositoryProvider = RepositoryProvider()

    val statsViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            StatsViewModel(
                userRepository = repositoryProvider.userRepository,
                matchRepository = repositoryProvider.matchRepository
            )
        }
    }
}
