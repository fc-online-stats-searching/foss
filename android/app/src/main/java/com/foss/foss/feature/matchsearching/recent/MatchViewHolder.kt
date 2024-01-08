package com.foss.foss.feature.matchsearching.recent

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foss.foss.R
import com.foss.foss.databinding.ItemMatchBinding
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.WinDrawLoseUiModel
import java.time.format.DateTimeFormatter

class MatchViewHolder(
    private val binding: ItemMatchBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(match: MatchUiModel) {
        with(binding) {
            itemMatchTvDate.text = match.date.format(
                DateTimeFormatter.ofPattern(
                    root.context.getString(
                        R.string.common_date_format
                    )
                )
            )
            itemMatchTvOpponentNickname.text = match.opponentName
            itemMatchTvMatchType.text = root.context.getString(match.matchType.resId)
            itemMatchTvWinDrawLose.text = root.context.getString(match.winDrawLose.resId)
            itemMatchTvScore.text = root.context.getString(
                R.string.common_score_format,
                match.point,
                match.otherPoint
            )
            itemMatchViewSideBar.setBackgroundColor(getSideBarColor(match.winDrawLose))
            itemMatchIvArrowDown.setColorFilter(getDropDownArrowColor(match.winDrawLose))
            itemMatchTvMatchType.setTextColor(getTextColor(match.winDrawLose))
            itemMatchTvWinDrawLose.setTextColor(getTextColor(match.winDrawLose))
            setManOfTheMatchImage(
                imageView = itemMatchIvManOfTheMatch,
                manOfTheMatch = match.manOfTheMatch
            )
        }
    }

    private fun getSideBarColor(winDrawLose: WinDrawLoseUiModel): Int = when (winDrawLose) {
        WinDrawLoseUiModel.WIN -> {
            binding.root
                .context
                .getColor(R.color.item_match_blue_side_bar)
        }

        WinDrawLoseUiModel.DRAW -> Color.LTGRAY

        WinDrawLoseUiModel.LOSE -> {
            binding.root
                .context
                .getColor(R.color.item_match_red_side_bar)
        }
    }

    private fun getTextColor(winDrawLose: WinDrawLoseUiModel): Int = when (winDrawLose) {
        WinDrawLoseUiModel.WIN -> {
            binding.root
                .context
                .getColor(R.color.item_match_blue_text)
        }

        WinDrawLoseUiModel.DRAW -> Color.LTGRAY

        WinDrawLoseUiModel.LOSE -> {
            binding.root
                .context
                .getColor(R.color.item_match_red_text)
        }
    }

    private fun getDropDownArrowColor(winDrawLose: WinDrawLoseUiModel): Int = when (winDrawLose) {
        WinDrawLoseUiModel.WIN -> {
            binding.root
                .context
                .getColor(R.color.item_match_blue_drop_down_arrow)
        }

        WinDrawLoseUiModel.DRAW -> Color.DKGRAY

        WinDrawLoseUiModel.LOSE -> {
            binding.root
                .context
                .getColor(R.color.item_match_red_drop_down_arrow)
        }
    }

    private fun setManOfTheMatchImage(imageView: ImageView, manOfTheMatch: String) {
        Glide.with(imageView.context)
            .load(manOfTheMatch)
            .error(R.drawable.eight)
            .into(imageView)
    }

    companion object {

        fun valueOf(parent: ViewGroup): MatchViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMatchBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return MatchViewHolder(binding)
        }
    }
}
