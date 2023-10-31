package com.example.teamproject.feature.legacy // package com.example.teamproject.feature
//
// import androidx.appcompat.app.AppCompatActivity
// import android.os.Bundle
// import android.util.Log
// import android.view.View
// import android.widget.Toast
// import androidx.activity.viewModels
// import androidx.lifecycle.ViewModel
// import androidx.lifecycle.ViewModelProvider
// import androidx.recyclerview.widget.LinearLayoutManager
// import com.example.teamproject.R
// import com.example.teamproject.data.RetrofitModule
// import com.example.teamproject.data.entity.response.MatchResponse
// import com.example.teamproject.data.entity.response.UserResponse
// import com.example.teamproject.databinding.ActivityMainBinding
// import com.example.teamproject.data.repository.FifaRepository
// import com.google.android.material.tabs.TabLayoutMediator
// import kotlinx.coroutines.CoroutineScope
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.launch
// import kotlinx.coroutines.withContext
// import retrofit2.Response
//
//
//
// class MainActivity : AppCompatActivity() {
//    lateinit var binding: ActivityMainBinding
//    val texterr = arrayListOf<String>("최근 전적", "플레이 유형", "상대 전적" )
//    var data=ArrayList<Response<MatchResponse>>()
//    val myViewModel : MyViewModel by viewModels(){
//        object : ViewModelProvider.Factory{
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                return MyViewModel(FifaRepository()) as T
//            }
//        }
//    }
//    var relativeStats = mutableMapOf<String, Array<Int>>()
//    companion object {
//        const val TAG = "MainActivity"
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        /////////////////
//        binding.testbutton.setOnClickListener {
//            Log.i(TAG, "onCreate:${binding.editText.text.toString()}")
//            myViewModel.getRankDataBySearchId(binding.editText.text.toString())
//            myViewModel.curRank.observe(this@MainActivity){
//                Log.i("ffff", myViewModel.curRank.value.toString())
//            }
//        }
//
//        //////////////////
//        binding.apply{
//            sendBtn.setOnClickListener {
//                myViewModel.getUserIdBySearchId(editText.text.toString())
//                myViewModel.getUserIdBySearchId2(editText.text.toString())
//                myViewModel.getRankDataBySearchId(editText.text.toString())
//            }
//            // 탭 레이아웃
//            viewpager.adapter = ViewPagerAdapter(this@MainActivity)
//            TabLayoutMediator(tabLayout, viewpager){
//                    tab, position->
//                tab.text = texterr[position]
//            }.attach()
//
//            myViewModel.stateProgressbar.observe(this@MainActivity){
//                if(it) {
//                    binding.progressBar.visibility=View.VISIBLE
//                    binding.viewpager.visibility=View.GONE
//                }
//                else{
//                    binding.progressBar.visibility=View.GONE
//                    binding.viewpager.visibility=View.VISIBLE
//                }
//            }
//
//            myViewModel.curMatchNum.observe(this@MainActivity){
//                    Log.e("실행", "${it}")
//                    if(it.isEmpty()) Toast.makeText(this@MainActivity, "해당 닉네임의 사용자는 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
//                    myViewModel.getMatchDataByMatchNum(it)
//
//            }
//            myViewModel.curMatchResponse.observe(this@MainActivity){
// //                Log.e("실행", it?.toString())
//            }
//
//
//            myViewModel.curMatchNum2.observe(this@MainActivity){
//                Log.e("실행", "${it}")
//                myViewModel.getMatchDataByMatchNum2(it)
//            }
//            myViewModel.curMatchResponse2.observe(this@MainActivity){
// //                Log.e("실행", it?.toString())
//            }
//
//        }
//    }
//    private fun initRecyclerView() {
//        adapter= Adapter(data)
//        binding.recyclerView.layoutManager= LinearLayoutManager(this,
//        LinearLayoutManager.VERTICAL,false)
//        binding.recyclerView.adapter=adapter
//    }
//
//    private fun unofficial_game_serach(searchId: String) {
//        relativeStats.clear()
//        data.clear()
//        CoroutineScope(Dispatchers.Main).launch {
//            var response_User: Response<UserResponse>
//            var response_MatchNum: List<String>
//            var response_MatchResponse: Response<MatchResponse>
//            //binding.recyclerView.visibility=View.GONE
//            binding.progressBar.visibility= View.VISIBLE
//            withContext(Dispatchers.IO) {
//                response_User = RetrofitModule.userService.fetchUser(
//                    nickname = searchId,
//                    value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMzE2MTA4IiwiYXV0aF9pZCI6IjIiLCJ0b2tlbl90eXBlIjoiQWNjZXNzVG9rZW4iLCJzZXJ2aWNlX2lkIjoiNDMwMDExNDgxIiwiWC1BcHAtUmF0ZS1MaW1pdCI6IjUwMDoxMCIsIm5iZiI6MTY1MjMxOTUwMCwiZXhwIjoxNjY3ODcxNTAwLCJpYXQiOjE2NTIzMTk1MDB9.K6XEURlQhBdKs_NnXoeZYmwubgx5W3jfb3tFLL27LnY"
//                )
//                if (response_User.body() != null) {
//                    val user_accessid: String? = response_User.body()?.accessId
//                    if (user_accessid != null) {
//                        response_MatchNum = RetrofitModule.matchService.fetchMatchNumber(
//                            value = application.getString(R.string.fifa_api_key),
//                            userAccessId = user_accessid,
//                            matchType = 40,
//                            offset = 0,
//                            limit = 10
//                        )
//                        Log.i("asdasd", "")
//                        if (response_MatchNum.size != 0) {
//                            for (i in response_MatchNum) {
//                                Log.i("match num : ", i)
//                                response_MatchResponse =
//                                    RetrofitModule.matchService.fetchMatch(
//                                        value = application.getString(R.string.fifa_api_key),
//                                        matchid = i
//                                    )
//                                data.add(response_MatchResponse)
//                                for (j in response_MatchResponse.body()?.matchInfoResponse!!) {
//                                    if (j.accessId == user_accessid) {
//                                        Log.i("${j.nickname.toString()}", "${j.matchDetailResponse.matchResult.toString()}")
//                                    }
//                                    else{
//                                        if(relativeStats.get(j.nickname)==null){
//                                            when(j.matchDetailResponse.matchResult){
//                                                "패"->{
// //                                                    val tmp=Array<Int>(2){1, 0}
//                                                    val tmp=arrayOf(1, 0)
//                                                    relativeStats.put(j.nickname.toString(), tmp)
// //                                                    Log.i(j.nickname.toString(), (relativeStats.get(j.nickname)!!.get(0)).toString()+"승 "+(relativeStats.get(j.nickname)!!.get(1)).toString()+"패")
//                                                    Log.i(tmp.get(0).toString(), tmp.get(1).toString())
//                                                }
//                                                "승"->{
// //                                                    val tmp=Array<Int>(2){0; 1}
//                                                    val tmp=arrayOf(1, 0)
//                                                    relativeStats.put(j.nickname.toString(), tmp)
// //                                                    Log.i(j.nickname.toString(), (relativeStats.get(j.nickname)!!.get(0)).toString()+"승 "+(relativeStats.get(j.nickname)!!.get(1)).toString()+"패")
//                                                    Log.i(tmp.get(0).toString(), tmp.get(1).toString())
//                                                }
//                                            }
//                                        }
//                                        else{
//                                            when(j.matchDetailResponse.matchResult){
//                                                "패"->{
//                                                    val tmp=Array<Int>(2){relativeStats.get(j.nickname)!!.get(0)+1; relativeStats.get(j.nickname)!!.get(1)}
//                                                    relativeStats.put(j.nickname.toString(), tmp)
//                                                    Log.i(j.nickname.toString(), (relativeStats.get(j.nickname)!!.get(0)).toString()+"승 "+(relativeStats.get(j.nickname)!!.get(1)).toString()+"패")
//                                                }
//                                                "승"->{
//                                                    val tmp=Array<Int>(2){relativeStats.get(j.nickname)!!.get(0); relativeStats.get(j.nickname)!!.get(1)+1}
//                                                    relativeStats.put(j.nickname.toString(), tmp)
//                                                    Log.i(j.nickname.toString(), (relativeStats.get(j.nickname)!!.get(0)).toString()+"승 "+(relativeStats.get(j.nickname)!!.get(1)).toString()+"패")
//                                                }
//                                            }
//                                        }
//                                        Log.i("${j.nickname.toString()}", "${j.matchDetailResponse.matchResult.toString()}")
//                                    }
//                                }
//                            }
//                        } else {
//                            Log.i("검색된 데이터가 없습니다.", "")
//                        }
//                    }
//                }
//            }
//            for((key, value) in relativeStats){
//                Log.i("${key}", "${value[0].toString()}승 ${value[1].toString()}패")
//            }
//
//            binding.progressBar.visibility= View.GONE
//            //binding.recyclerView.visibility=View.VISIBLE
//            adapter.notifyDataSetChanged()
//        }
//    }
//    private fun official_game_serach(searchId: String) {
//        data.clear()
//        CoroutineScope(Dispatchers.Main).launch {
//            var response_User: Response<UserResponse>
//            var response_MatchNum: List<String>
//            var response_MatchResponse: Response<MatchResponse>
//            binding.recyclerView.visibility = View.GONE
//            binding.progressBar.visibility = View.VISIBLE
//            withContext(Dispatchers.IO) {
//                response_User = RetrofitModule.userService.fetchUser(
//                    value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMzE2MTA4IiwiYXV0aF9pZCI6IjIiLCJ0b2tlbl90eXBlIjoiQWNjZXNzVG9rZW4iLCJzZXJ2aWNlX2lkIjoiNDMwMDExNDgxIiwiWC1BcHAtUmF0ZS1MaW1pdCI6IjUwMDoxMCIsIm5iZiI6MTY1MjMxOTUwMCwiZXhwIjoxNjY3ODcxNTAwLCJpYXQiOjE2NTIzMTk1MDB9.K6XEURlQhBdKs_NnXoeZYmwubgx5W3jfb3tFLL27LnY",
//                    nickname = searchId
//                )
//                if (response_User.body() != null) {
//                    val user_accessid: String? = response_User.body()?.accessId
//                    if (user_accessid != null) {
//                        response_MatchNum = RetrofitModule.matchService.fetchMatchNumber(
//                            value = application.getString(R.string.fifa_api_key),
//                            userAccessId = user_accessid,
//                            matchType = 50,
//                            offset = 0,
//                            limit = 10
//                        )
//
//                        if (response_MatchNum.size != 0) {
//                            for (i in response_MatchNum) {
//                                Log.i("match num : ", i)
//                                response_MatchResponse =
//                                    RetrofitModule.matchService.fetchMatch(
//                                        value = application.getString(R.string.fifa_api_key),
//                                        matchid = i
//                                    )
//                                data.add(response_MatchResponse)
//                                for (j in response_MatchResponse.body()?.matchInfoResponse!!) {
//                                    if (j.accessId == user_accessid) {
//                                        Log.i(
//                                            "${j.nickname.toString()}",
//                                            "${j.matchDetailResponse.matchResult.toString()}"
//                                        )
//                                    } else {
//                                        Log.i(
//                                            "${j.nickname.toString()}",
//                                            "${j.matchDetailResponse.matchResult.toString()}"
//                                        )
//                                    }
//                                }
//                            }
//                        } else {
//                            Log.i("검색된 데이터가 없습니다.", "")
//                        }
//                    }
//                }
//            }
//            binding.progressBar.visibility = View.GONE
//            binding.recyclerView.visibility = View.VISIBLE
//            adapter.notifyDataSetChanged()
//        }
//    }
// }
