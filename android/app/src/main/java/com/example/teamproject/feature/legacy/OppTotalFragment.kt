package com.example.teamproject.feature.legacy // package com.example.teamproject.feature
//
// import android.os.Bundle
// import android.util.Log
// import androidx.fragment.app.Fragment
// import android.view.LayoutInflater
// import android.view.View
// import android.view.ViewGroup
// import androidx.fragment.app.activityViewModels
// import androidx.recyclerview.widget.LinearLayoutManager
// import com.example.teamproject.databinding.FragmentOppTotalBinding
//
// class OppTotalFragment : Fragment() {
//    var data: ArrayList<OppData> = ArrayList()
//    lateinit var adapter: RecyclerViewAdapter2
//    val myViewModel: MyViewModel by activityViewModels()
//    var binding: FragmentOppTotalBinding? = null
//    private var columnCount = 1
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentOppTotalBinding.inflate(layoutInflater)
//        return binding!!.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        adapter= RecyclerViewAdapter2(data)
//        binding!!.recyclerView.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        binding!!.recyclerView.adapter = adapter
//        myViewModel.curMatchResponse2.observe(requireActivity()) {
//            data.clear()
//            var relativeStats = mutableMapOf<String, Array<Int>>()
//            for (i in it) {
//                for (j in i.matchInfoResponse) {
//                    if (myViewModel.UserId.value != j.nickname) {
//                        Log.i("몇번 드갈까요", "ㄹ")
//                        if (relativeStats.get(j.nickname) == null) {
//                            when (j.matchDetailResponse.matchResult) {
//                                "승" -> {
//                                    val tmp = arrayOf(0, 1)
//                                    relativeStats.put(j.nickname.toString(), tmp)
// //                                    Log.i(tmp.get(0).toString(), tmp.get(1).toString())
//                                }
//                                "패" -> {
//                                    val tmp = arrayOf(1, 0)
//                                    relativeStats.put(j.nickname.toString(), tmp)
// //                                    Log.i(tmp.get(0).toString(), tmp.get(1).toString())
//                                }
//                            }
//                        } else {
//                            when (j.matchDetailResponse.matchResult) {
//                                "패" -> {
//                                    val tmp = arrayOf(
//                                        relativeStats.get(j.nickname)!!.get(0) + 1,
//                                        relativeStats.get(j.nickname)!!.get(1)
//                                    )
//                                    relativeStats.put(j.nickname.toString(), tmp)
// //                                    Log.i(
// //                                        j.nickname.toString(),
// //                                        (relativeStats.get(j.nickname)!!.get(0)).toString() + "승 " + (relativeStats.get(j.nickname)!!.get(1)).toString() + "패"
// //                                    )
//                                }
//                                "승" -> {
//                                    val tmp = arrayOf(
//                                        relativeStats.get(j.nickname)!!.get(0),
//                                        relativeStats.get(j.nickname)!!.get(1) + 1
//                                    )
//                                    relativeStats.put(j.nickname.toString(), tmp)
// //                                    Log.i(
// //                                        j.nickname.toString(),
// //                                        (relativeStats.get(j.nickname)!!.get(0)).toString() + "승 " + (relativeStats.get(j.nickname)!!.get(1)).toString() + "패"
// //                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            for (i in relativeStats.keys) {
//                Log.i("${i}", "${relativeStats.get(i)!!.get(0)}승, ${relativeStats.get(i)!!.get(1)}패")
//                data.add(OppData(i, arrayOf(relativeStats.get(i)!!.get(0), relativeStats.get(i)!!.get(1))))
//            }
//            adapter.notifyDataSetChanged()
//
//        }
//    }
// }
