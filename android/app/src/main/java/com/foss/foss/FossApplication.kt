package com.foss.foss

import com.boogiwoogi.woogidi.application.DiApplication
import com.boogiwoogi.woogidi.pure.Instance
import com.foss.foss.data.FakeMatchRepository
import com.foss.foss.data.repository.RelativeMatchRepositoryImpl
import com.foss.foss.di.auto.RemoteDataSourceModule
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.RelativeMatchRepository

class FossApplication : DiApplication() {

    override fun onCreate() {
        super.onCreate()

        with(injector) {
            applicationContainer.add(
                Instance<MatchRepository>(
                    FakeMatchRepository()
                )
            )

//            applicationContainer.add(
//                Instance<RelativeMatchRepository>(
//                    FakeRelativeMatchRepository()
//                )
//            )

            applicationContainer.add(
                Instance<RelativeMatchRepository>(
                    RelativeMatchRepositoryImpl(
                        injector.inject(module = RemoteDataSourceModule)
                    )
                )
            )

//            applicationContainer.add(
//                Instance<UserRepository>(
//                    DefaultUserRepository(
//                        inject(module = RemoteDataSourceModule)
//                    )
//                )
//            )
//
//            applicationContainer.add(
//                Instance<MatchRepository>(
//                    DefaultMatchRepository(
//                        inject(module = RemoteDataSourceModule)
//                    )
//                )
//            )
        }
    }
}
