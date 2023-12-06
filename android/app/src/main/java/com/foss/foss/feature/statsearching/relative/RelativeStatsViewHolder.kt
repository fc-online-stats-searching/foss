package com.foss.foss.feature.statsearching.relative

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foss.foss.R
import com.foss.foss.databinding.ItemRelativeStatsBinding
import com.foss.foss.model.RelativeStatsUiModel

class RelativeStatsViewHolder private constructor(
    private val binding: ItemRelativeStatsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: RelativeStatsUiModel) {
        with(binding) {
            itemRelativeTvName.text = data.opponentName
            itemRelativeTvStats.text = data.matchResult
            itemRelativeTvLastMatch.text = data.lastMatchData
            itemRelativeTvGoal.text =
                itemView.context.getString(R.string.item_relative_stats_score, data.goal)
            itemRelativeTvConceded.text =
                itemView.context.getString(R.string.item_relative_stats_score, data.conceded)
        }
    }

    companion object {
        fun from(parent: ViewGroup): RelativeStatsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRelativeStatsBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return RelativeStatsViewHolder(binding)
        }
    }
}
