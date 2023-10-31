package com.example.teamproject.feature.legacy // package com.example.teamproject.feature
//
// import android.graphics.Color
// import android.util.Log
// import android.view.LayoutInflater
// import android.view.View
// import android.view.ViewGroup
// import androidx.recyclerview.widget.RecyclerView
// import com.example.teamproject.data.entity.response.MatchResponse
// import com.example.teamproject.databinding.FragmentItemBinding
//
// class RecyclerViewAdapter(var items: ArrayList<MatchResponse>) :
//    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
//    inner class ViewHolder(val binding: FragmentItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        init {
//            binding.resultrow.setOnClickListener {
//                clickListener?.ItemClickListener(items[adapterPosition])
//                notifyDataSetChanged()
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            FragmentItemBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//        )
//    }
//
//    interface OnItemClickListener {
//        fun ItemClickListener(item: MatchResponse)
//    }
//
//    var clickListener: OnItemClickListener? = null
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        var cur_user: String? = null
//
//        if ((items[0].matchInfoResponse.get(0).nickname == items[1].matchInfoResponse.get(0).nickname)
//            || (items[0].matchInfoResponse.get(0).nickname == items[1].matchInfoResponse.get(1).nickname)
//        ) cur_user = items[0].matchInfoResponse.get(0).nickname
//
//        if ((items[0].matchInfoResponse.get(1).nickname == items[1].matchInfoResponse.get(0).nickname)
//            || (items[0].matchInfoResponse.get(1).nickname == items[1].matchInfoResponse.get(1).nickname)
//        ) cur_user = items[0].matchInfoResponse.get(1).nickname
//
//
//        val user1 = items[position].matchInfoResponse[0].nickname.toString()
//        val user2 = items[position].matchInfoResponse[1].nickname.toString()
//
//
//        holder.binding.resultrow.text = user1 + " VS " + user2
//        holder.binding.score.text =
//            user1 + " " + items[position].matchInfoResponse[0].shootResponse.goalTotal.toString() + " : " + items[position].matchInfoResponse[1].shootResponse.goalTotal.toString() + " " + user2
// //        holder.binding.resultrow.setBackgroundColor()
//
//
//        if (cur_user == items[position].matchInfoResponse[0].nickname && items[position].matchInfoResponse[0].matchDetailResponse.matchResult == "패") holder.binding.resultrow.setBackgroundColor(
//            Color.parseColor("#FFEEEE")
//        )
//        if (cur_user == items[position].matchInfoResponse[0].nickname && items[position].matchInfoResponse[0].matchDetailResponse.matchResult == "승") holder.binding.resultrow.setBackgroundColor(
//            Color.parseColor("#D4E4FE")
//        )
//
//        if (cur_user == items[position].matchInfoResponse[1].nickname) {
//            if (items[position].matchInfoResponse[1].matchDetailResponse.matchResult == "패")
//                holder.binding.resultrow.setBackgroundColor(Color.parseColor("#FFEEEE"))
//            else if (items[position].matchInfoResponse[1].matchDetailResponse.matchResult == "승")
//                holder.binding.resultrow.setBackgroundColor(Color.parseColor("#D4E4FE"))
//        }
//        if (cur_user == items[position].matchInfoResponse[1].nickname && items[position].matchInfoResponse[1].matchDetailResponse.matchResult == "승") holder.binding.resultrow.setBackgroundColor(
//            Color.parseColor("#D4E4FE")
//        )
//
//        Log.i("ffffffffffffffffffff", cur_user!!)
//
//
//
//        holder.binding.user1Detail.text =
//            "페널티 박스 안에서의 슛 : " + items[position].matchInfoResponse[0].shootResponse.goalInPenalty.toString() + "/" + items[position].matchInfoResponse[0].shootResponse.shootInPenalty.toString() + '\n' +
//                    "중거리 슛 : " + items[position].matchInfoResponse[0].shootResponse.goalOutPenalty.toString() + "/" + items[position].matchInfoResponse[0].shootResponse.shootInPenalty.toString() + '\n' +
//                    "헤딩 슛: " + items[position].matchInfoResponse[0].shootResponse.goalHeading.toString() + "/" + items[position].matchInfoResponse[0].shootResponse.shootHeading.toString() + '\n' +
//                    "짧은 패스 : " + items[position].matchInfoResponse[0].passResponse.shortPassSuccess.toString() + "/" + items[position].matchInfoResponse[0].passResponse.shortPassTry.toString() + '\n' +
//                    "긴 패스 : " + items[position].matchInfoResponse[0].passResponse.longPassSuccess.toString() + "/" + items[position].matchInfoResponse[0].passResponse.longPassTry.toString() + '\n' +
//                    "스루 패스 : " + items[position].matchInfoResponse[0].passResponse.throughPassSuccess.toString() + "/" + items[position].matchInfoResponse[0].passResponse.throughPassTry.toString()
//
//        holder.binding.user2Detail.text =
//            "페널티 박스 안에서의 슛 : " + items[position].matchInfoResponse[1].shootResponse.goalInPenalty.toString() + "/" + items[position].matchInfoResponse[1].shootResponse.shootInPenalty.toString() + '\n' +
//                    "중거리 슛 : " + items[position].matchInfoResponse[1].shootResponse.goalOutPenalty.toString() + "/" + items[position].matchInfoResponse[1].shootResponse.shootInPenalty.toString() + '\n' +
//                    "헤딩 슛: " + items[position].matchInfoResponse[1].shootResponse.goalHeading.toString() + "/" + items[position].matchInfoResponse[1].shootResponse.shootHeading.toString() + '\n' +
//                    "짧은 패스 : " + items[position].matchInfoResponse[1].passResponse.shortPassSuccess.toString() + "/" + items[position].matchInfoResponse[1].passResponse.shortPassTry.toString() + '\n' +
//                    "긴 패스 : " + items[position].matchInfoResponse[1].passResponse.longPassSuccess.toString() + "/" + items[position].matchInfoResponse[1].passResponse.longPassTry.toString() + '\n' +
//                    "스루 패스 : " + items[position].matchInfoResponse[1].passResponse.throughPassSuccess.toString() + "/" + items[position].matchInfoResponse[1].passResponse.throughPassTry.toString()
//
//
//        if (!items[position].isClicked) holder.binding.resultDetail.visibility = View.GONE
//        else holder.binding.resultDetail.visibility = View.VISIBLE
//    }
//
//    override fun getItemCount(): Int = items.size
//
//
// }
