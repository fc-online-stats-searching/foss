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
// import com.example.teamproject.data.entity.response.MatchResponse
// import com.example.teamproject.feature.MainActivity.Companion.TAG
// import com.example.teamproject.databinding.FragmentRecentTotalBinding
//
//
// class RecentTotalFragment : Fragment() {
//    var binding: FragmentRecentTotalBinding? = null
//    var data: ArrayList<MatchResponse> = ArrayList()
//    var adapter = RecyclerViewAdapter(data)
//    private val myViewModel : MyViewModel by activityViewModels()
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding= FragmentRecentTotalBinding.inflate(layoutInflater)
//        return binding!!.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        adapter.clickListener=object: RecyclerViewAdapter.OnItemClickListener {
//            override fun ItemClickListener(item: MatchResponse) {
//                item.isClicked=!item.isClicked
//            }
//        }
//        binding!!.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        binding!!.recyclerView.adapter=adapter
//        myViewModel.curMatchResponse.observe(requireActivity()){
//            Log.e(TAG, "onViewCreated: 레아웃변경", )
//            adapter.items = it
//            adapter.notifyDataSetChanged()
//        }
//
//    }
//
// }
