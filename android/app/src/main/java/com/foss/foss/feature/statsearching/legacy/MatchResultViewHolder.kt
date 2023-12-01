// package com.foss.foss.feature.statsearching.legacy
//
// import android.view.LayoutInflater
// import android.view.ViewGroup
// import androidx.recyclerview.widget.RecyclerView
// import com.foss.foss.R
// import com.foss.foss.databinding.ItemMatchResultBinding
// import com.foss.foss.model.MatchUiModel
//
// class MatchResultViewHolder(
//    private val binding: ItemMatchResultBinding
// ) : RecyclerView.ViewHolder(binding.root) {
//
//    fun bind(matchUiModel: MatchUiModel) {
//        with(binding) {
//            matchResult = matchUiModel
//            matchResultTvScore.text = root.context.getString(
//                R.string.item_match_result_score,
//                matchUiModel.point,
//                matchUiModel.otherPoint
//            )
//        }
//    }
//
//    companion object {
//
//        fun valueOf(parent: ViewGroup): MatchResultViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ItemMatchResultBinding.inflate(
//                layoutInflater,
//                parent,
//                false
//            )
//
//            return MatchResultViewHolder(binding)
//        }
//    }
// }
