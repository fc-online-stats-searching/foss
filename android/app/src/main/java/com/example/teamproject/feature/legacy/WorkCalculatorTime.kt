package com.example.teamproject.feature.legacy // package com.example.teamproject
//
// import android.util.Log
// import java.util.*
//
//
// fun getSixHourIntervalTime(): Long{
//
//    val currentDate = Calendar.getInstance()
//    val hour = currentDate.get(Calendar.HOUR_OF_DAY)
//    var dueDate = Calendar.getInstance()
//    Log.e("현재시간",hour.toString())
//    if(hour < 4 || 22< hour){
//        dueDate.apply {
//
//            set(Calendar.HOUR_OF_DAY, 4)
//            set(Calendar.MINUTE, 0)
//            set(Calendar.SECOND, 0) }
//    }
//    else if(4<= hour || hour < 10) {
//        dueDate.apply {
//
//            set(Calendar.HOUR_OF_DAY, 10)
//            set(Calendar.MINUTE, 0)
//            set(Calendar.SECOND, 0)
//        }
//    }
//    else if(10<= hour || hour < 16) {
//        dueDate.apply {
//
//            set(Calendar.HOUR_OF_DAY, 16)
//            set(Calendar.MINUTE, 0)
//            set(Calendar.SECOND, 0)
//        }
//    }
//    else if(16<= hour || hour < 22) {
//        dueDate.apply {
//
//            set(Calendar.HOUR_OF_DAY, 22)
//            set(Calendar.MINUTE, 0)
//            set(Calendar.SECOND, 0)
//        }
//    }
//    Log.e("6시간마다","6시간함수 울림")
//    if (dueDate.before(currentDate))
//        dueDate.add(Calendar.HOUR_OF_DAY, 6)
//    val dueDa = dueDate.toString()
//    Log.e("duedata",dueDa)
//
//    return dueDate.timeInMillis - currentDate.timeInMillis
// }
//
// //test 1분마다 알람 울리기
// fun getOneMinIntervalTime(): Long{
//    Log.e("1분마다","1분함수 울림")
//    val currentDate = Calendar.getInstance()
//    val dueDate = Calendar.getInstance().apply {
//        add(Calendar.MINUTE, 1)
//    }
//    return dueDate.timeInMillis - currentDate.timeInMillis
// }
