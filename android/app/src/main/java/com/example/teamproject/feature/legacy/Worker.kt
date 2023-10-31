package com.example.teamproject.feature.legacy // package com.example.teamproject
//
// import android.app.Notification
// import android.app.NotificationChannel
// import android.app.NotificationManager
// import android.app.PendingIntent
// import android.content.Context
// import android.content.Intent
// import android.os.Build
// import android.util.Log
// import androidx.core.app.NotificationCompat
// import androidx.core.content.ContextCompat.getSystemService
// import androidx.work.*
// import androidx.work.WorkerParameters
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.coroutineScope
// import kotlinx.coroutines.withContext
// import java.util.concurrent.TimeUnit
//
//
// var cnt = 1
// class Worker(appContext: Context, parameters: WorkerParameters) : CoroutineWorker(appContext, parameters) {
//    private val channelID = "channel1"
//    private var notificationManager: NotificationManager? = null
//
//    companion object{
//        const val WORK_NAME = "Notification Work"
//    }
//
//    override suspend fun doWork(): Result {
//
//        try {
//            Log.e(WORK_NAME, "DoWork")
//
//            notificationManager =applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            createNotificationChannel()
//            displayNotification()
//
//            val constraints = Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresBatteryNotLow(true)
//                .build()
//
//            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<Worker>().
//            setInitialDelay(getOneMinIntervalTime(), TimeUnit.MILLISECONDS).
//            setConstraints(constraints).build()
// //            setInitialDelay(getSixHourIntervalTime(), TimeUnit.MILLISECONDS).
//
//            //실행 횟수 체크
//            cnt++
//            Log.e("실행횟수 : ", cnt.toString() )
//
//            WorkManager.getInstance(applicationContext).enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)
//
//        }catch (e: Exception){
//            Result.retry()
//        }
//
//        return Result.success()
//    }
//
//    private fun displayNotification() {
//        //채널 ID
//        val notificationId = 45
//        //알림의 탭 작업 설정
//        val tapResultIntent = Intent(applicationContext, MainActivity::class.java).apply {
//
//            //이전에 실행된 액티비티들을 모두 없엔 후 새로운 액티비티 실행 플래그
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(
//            applicationContext,
//            0,
//            tapResultIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        val notification: Notification = NotificationCompat.Builder(applicationContext, channelID)
//            .setContentTitle("FIFA 전적 검색") // 노티 제목
//            .setContentText("FIFA 전적이 업데이트 되었습니다!") // 노티 내용
//            .setSmallIcon(android.R.drawable.ic_dialog_info) //아이콘이미지
//            .setAutoCancel(true) // 사용자가 알림을 탭하면 자동으로 알림을 삭제
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent) //노티클릭시 인텐트작업
//            .setWhen(System.currentTimeMillis())
//            .build()
//        notificationManager?.notify(notificationId, notification)
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //중요도
//            val importance = NotificationManager.IMPORTANCE_HIGH
//            //채널 생성
//            val channel = NotificationChannel(channelID, "FIFAChannel", importance).apply {
//                description =  "FIFA Demo"
//            }
//            notificationManager?.createNotificationChannel(channel)
//        } else {
//        }
//    }
// }
