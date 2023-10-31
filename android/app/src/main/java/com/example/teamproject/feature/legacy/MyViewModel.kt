package com.example.teamproject.feature.legacy // package com.example.teamproject.feature
//
// import androidx.lifecycle.MutableLiveData
// import androidx.lifecycle.ViewModel
// import androidx.lifecycle.viewModelScope
// import com.example.teamproject.data.entity.response.MatchResponse
// import com.example.teamproject.data.entity.response.RankResponse
// import com.example.teamproject.data.repository.FifaRepository
// import kotlinx.coroutines.launch
// import retrofit2.Response
//
// class MyViewModel(private val fifaRepository: FifaRepository) : ViewModel() {
//    var data = mutableListOf<ArrayList<Response<MatchResponse>>>()
//    var UserId = MutableLiveData<String>()
//    val curMatchNum = MutableLiveData<List<String>>()
//    val curMatchNum2 = MutableLiveData<List<String>>()
//    var curMatchResponse = MutableLiveData<ArrayList<MatchResponse>>()
//    var curMatchResponse2= MutableLiveData<ArrayList<MatchResponse>>()
//    var stateProgressbar = MutableLiveData<Boolean>()
//    var curRank =MutableLiveData<ArrayList<RankResponse>>()
//
//
//    fun getUserIdBySearchId(searchId: String) = viewModelScope.launch {
//        UserId.value=searchId
//        stateProgressbar.value=true
//        fifaRepository.getMatchNumBySearchId(searchId).let {
//            curMatchNum.postValue(it)
//        }
//    }
//
//    fun getUserIdBySearchId2(searchId: String) = viewModelScope.launch {
//        UserId.value=searchId
//        stateProgressbar.value=true
//        fifaRepository.getMatchNumBySearchId2(searchId).let {
//            curMatchNum2.postValue(it)
//        }
//
//    }
//
//    fun getRankDataBySearchId(searchId: String)=viewModelScope.launch {
//        curRank.value?.clear()
//        UserId.value=searchId
//        stateProgressbar.value=true
//        fifaRepository.getRankBySearchId(searchId).let{
//            curRank.postValue(it)
// //            stateProgressbar.value=false
//        }
//
// //        Log.i(curRank.value.toString(), "ffffffffff")
//    }
//
//    fun getMatchDataByMatchNum(matchNum: List<String>) = viewModelScope.launch {
//        fifaRepository.getMatchDataByMatchNum(matchNum).let{
//            curMatchResponse.postValue(it)
//            stateProgressbar.value=false
// //            Log.e("어디가", "문젱")
//        }
//    }
//
//
//    fun getMatchDataByMatchNum2(matchNum: List<String>) = viewModelScope.launch {
//
//        fifaRepository.getMatchDataByMatchNum(matchNum).let{
//            curMatchResponse2.postValue(it)
//            stateProgressbar.value=false
// //            Log.e("어디가", "문젱")
//        }
//    }
//
//
// }
//
//
