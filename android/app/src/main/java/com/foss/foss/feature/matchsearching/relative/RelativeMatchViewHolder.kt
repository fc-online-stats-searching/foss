package com.foss.foss.feature.matchsearching.relative

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foss.foss.R
import com.foss.foss.databinding.ItemRelativeMatchBinding
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.RelativeMatchUiModel
import java.time.format.DateTimeFormatter

class RelativeMatchViewHolder private constructor(
    private val binding: ItemRelativeMatchBinding,
    private val onClick: (opponentNickname: String, matchDetails: ArrayList<MatchUiModel>) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var matchDetails: ArrayList<MatchUiModel>? = null
    private var opponentNickname: String? = null

    init {
        binding.root.setOnClickListener {
            if (matchDetails != null && opponentNickname != null) {
                onClick(opponentNickname!!, matchDetails!!)
            }
        }
    }

    fun bind(relativeMatch: RelativeMatchUiModel) {
        with(binding) {
            itemRelativeTvName.text = relativeMatch.opponentName
            itemRelativeTvMatches.text = itemView.context.getString(
                R.string.item_relative_matches_win_draw_loses_format,
                relativeMatch.numberOfGames,
                relativeMatch.numberOfWins,
                relativeMatch.numberOfDraws,
                relativeMatch.numberOfLoses
            )
            itemRelativeTvLastMatch.text = relativeMatch.recentMatchDate.format(
                DateTimeFormatter.ofPattern(itemView.context.getString(R.string.common_date_format))
            )
            itemRelativeTvGoal.text = itemView.context.getString(
                R.string.item_relative_matches_score,
                relativeMatch.goal
            )
            itemRelativeTvConceded.text = itemView.context.getString(
                R.string.item_relative_matches_score,
                relativeMatch.conceded
            )
            itemRelativeViewResult.setBackgroundColor(
                setTextColor(
                    relativeMatch.numberOfGames,
                    relativeMatch.numberOfWins
                )
            )
        }
        matchDetails = ArrayList(relativeMatch.matchDetails)
        opponentNickname = relativeMatch.opponentName
    }

    private fun setTextColor(
        numberOfGames: Int,
        numberOfWins: Int
    ): Int {
        val rate = (numberOfWins.toDouble() / numberOfGames) * 100.0
        val colorResource = if (rate >= 50.0) {
            R.color.item_relative_match_win_color
        } else {
            R.color.item_relative_match_lose_color
        }

        return binding.root.context.getColor(colorResource)
    }

    companion object {

        fun from(
            parent: ViewGroup,
            onClick: (opponentNickname: String, matchDetails: ArrayList<MatchUiModel>) -> Unit
        ): RelativeMatchViewHolder {
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
