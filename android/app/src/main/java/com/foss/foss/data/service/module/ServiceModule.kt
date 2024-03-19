package com.foss.foss.data.service.module

import com.foss.foss.BuildConfig
import com.foss.foss.data.service.RecentMatchService
import com.foss.foss.data.service.RelativeMatchService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()

    @Provides
    @Singleton
    fun providesRecentMatchService(retrofit: Retrofit): RecentMatchService =
        retrofit.create(RecentMatchService::class.java)

    @Provides
    @Singleton
    fun providesRelativeMatchService(retrofit: Retrofit): RelativeMatchService =
        retrofit.create(RelativeMatchService::class.java)
}
