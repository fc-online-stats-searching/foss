package com.example.teamproject.feature.legacy // package com.example.teamproject
//
// import android.app.*
// import android.content.Context
// import android.content.Intent
// import android.os.Build
// import android.util.Log
// import androidx.core.app.NotificationCompat
// import androidx.work.*
// import kotlinx.coroutines.CoroutineScope
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.launch
// import java.util.concurrent.TimeUnit
//
// class WorkApplication : Application() {
//
//    private val channelID = "channel1"
//    private var notificationManager: NotificationManager? = null
//
//    companion object {
//        const val TAG = "WorkApplication"
//    }
//
//    private val backgroundCoroutineScope = CoroutineScope(Dispatchers.Default)
//
//    override fun onCreate() {
//
//        super.onCreate()
//        delayCreateWork()
//        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    }
//
//    private fun delayCreateWork() {
//        backgroundCoroutineScope.launch {
//            createWorkManager()
//        }
//    }
//
//    private fun createWorkManager() {
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//
//        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<Worker>().setInitialDelay(
// //            getSixHourIntervalTime(),
//            getOneMinIntervalTime(),
//            TimeUnit.MILLISECONDS
//        ).setConstraints(constraints).build()
//
//        Log.e(TAG, "Init WorkManager")
//        WorkManager.getInstance(applicationContext)
//            .enqueueUniqueWork("Notification Work", ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)
//    }
// }
