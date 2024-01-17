package com.foss.foss

import com.boogiwoogi.woogidi.application.DiApplication
import com.boogiwoogi.woogidi.pure.Instance
import com.foss.foss.data.repository.DefaultMatchRepository
import com.foss.foss.data.repository.DefaultRelativeMatchRepository
import com.foss.foss.di.auto.RetrofitModule
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.RelativeMatchRepository

class FossApplication : DiApplication() {

    override fun onCreate() {
        super.onCreate()

        with(injector) {
            applicationContainer.add(
                Instance<MatchRepository>(
                    DefaultMatchRepository(
                        injector.inject(module = RetrofitModule)
                    )
                )
            )

            applicationContainer.add(
                Instance<RelativeMatchRepository>(
                    DefaultRelativeMatchRepository(
                        injector.inject(module = RetrofitModule)
                    )
                )
            )
        }
    }
}
