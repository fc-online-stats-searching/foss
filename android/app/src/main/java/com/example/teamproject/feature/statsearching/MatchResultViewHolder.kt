package com.example.teamproject.feature.statsearching

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.R
import com.example.teamproject.databinding.ItemMatchResultBinding
import com.example.teamproject.model.MatchResultUiModel

class MatchResultViewHolder(
    private val binding: ItemMatchResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(matchResultUiModel: MatchResultUiModel) {
        with(binding) {
            matchResult = matchResultUiModel
            matchResultTvScore.text = root.context.getString(
                R.string.item_match_result_score,
                matchResultUiModel.point,
                matchResultUiModel.otherPoint
            )
        }
    }

    companion object {

        fun valueOf(parent: ViewGroup): MatchResultViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMatchResultBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return MatchResultViewHolder(binding)
        }
    }
}
