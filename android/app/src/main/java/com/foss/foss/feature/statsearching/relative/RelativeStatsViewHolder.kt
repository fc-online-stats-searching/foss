package com.foss.foss.feature.statsearching.relative

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foss.foss.R
import com.foss.foss.databinding.ItemRelativeStatsBinding
import com.foss.foss.model.RelativeStatUiModel
import java.time.format.DateTimeFormatter

class RelativeStatsViewHolder private constructor(
    private val binding: ItemRelativeStatsBinding,
    private val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    fun bind(relativeStat: RelativeStatUiModel) {
        with(binding) {
            itemRelativeTvName.text = relativeStat.opponentName
            itemRelativeTvStats.text = itemView.context.getString(
                R.string.item_relative_stats_win_draw_loses_format,
                relativeStat.numberOfGames,
                relativeStat.numberOfWins,
                relativeStat.numberOfDraws,
                relativeStat.numberOfLoses
            )
            itemRelativeTvLastMatch.text = relativeStat.recentMatchDate.format(
                DateTimeFormatter.ofPattern(itemView.context.getString(R.string.common_date_format))
            )
            itemRelativeTvGoal.text = itemView.context.getString(
                R.string.item_relative_stats_score,
                relativeStat.goal
            )
            itemRelativeTvConceded.text = itemView.context.getString(
                R.string.item_relative_stats_score,
                relativeStat.conceded
            )
        }
    }

    companion object {

        fun from(parent: ViewGroup, onClick: (Int) -> Unit): RelativeStatsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRelativeStatsBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return RelativeStatsViewHolder(binding, onClick)
        }
    }
}
