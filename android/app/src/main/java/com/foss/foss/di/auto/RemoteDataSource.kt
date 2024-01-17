package com.foss.foss.di.auto

import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Provides
import com.boogiwoogi.woogidi.pure.Singleton
import com.foss.foss.BuildConfig
import com.foss.foss.data.service.RecentMatchService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object RemoteDataSource : DefaultModule() {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
    }

//    @Provides
//    @Singleton
//    fun providesUserService(): UserService {
//        return retrofit.create(UserService::class.java)
//    }
//
    @Provides
    @Singleton
    fun providesMatchService(): RecentMatchService {
        return retrofit.create(RecentMatchService::class.java)
    }
}
