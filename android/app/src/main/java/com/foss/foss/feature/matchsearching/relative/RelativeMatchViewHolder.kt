package com.foss.foss.feature.matchsearching.relative

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foss.foss.R
import com.foss.foss.databinding.ItemRelativeMatchBinding
import com.foss.foss.model.RelativeMatchUiModel
import java.time.format.DateTimeFormatter

class RelativeMatchViewHolder private constructor(
    private val binding: ItemRelativeMatchBinding,
    private val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    fun bind(relativeStat: RelativeMatchUiModel) {
        with(binding) {
            itemRelativeTvName.text = relativeStat.opponentName
            itemRelativeTvMatches.text = itemView.context.getString(
                R.string.item_relative_matches_win_draw_loses_format,
                relativeStat.numberOfGames,
                relativeStat.numberOfWins,
                relativeStat.numberOfDraws,
                relativeStat.numberOfLoses
            )
            itemRelativeTvLastMatch.text = relativeStat.recentMatchDate.format(
                DateTimeFormatter.ofPattern(itemView.context.getString(R.string.common_date_format))
            )
            itemRelativeTvGoal.text = itemView.context.getString(
                R.string.item_relative_matches_score,
                relativeStat.goal
            )
            itemRelativeTvConceded.text = itemView.context.getString(
                R.string.item_relative_matches_score,
                relativeStat.conceded
            )
        }
    }

    companion object {

        fun from(parent: ViewGroup, onClick: (Int) -> Unit): RelativeMatchViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRelativeMatchBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return RelativeMatchViewHolder(binding, onClick)
        }
    }
}
