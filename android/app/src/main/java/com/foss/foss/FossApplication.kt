package com.foss.foss

import com.boogiwoogi.woogidi.application.DiApplication
import com.boogiwoogi.woogidi.pure.Instance
import com.foss.foss.data.legacy.repository.DefaultMatchRepository
import com.foss.foss.data.legacy.repository.DefaultUserRepository
import com.foss.foss.di.auto.RemoteDataSourceModule
import com.foss.foss.repository.legacy.MatchRepository
import com.foss.foss.repository.legacy.UserRepository

class FossApplication : DiApplication() {

    override fun onCreate() {
        super.onCreate()

        with(injector) {
            applicationContainer.add(
                Instance<UserRepository>(
                    DefaultUserRepository(
                        inject(module = RemoteDataSourceModule)
                    )
                )
            )

            applicationContainer.add(
                Instance<MatchRepository>(
                    DefaultMatchRepository(
                        inject(module = RemoteDataSourceModule)
                    )
                )
            )
        }
    }
}
