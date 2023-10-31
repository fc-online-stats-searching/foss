package com.example.teamproject.feature.statsearching

import androidx.recyclerview.widget.DiffUtil
import com.example.teamproject.model.MatchResultUiModel

class MatchResultDiffUtilCallBack : DiffUtil.ItemCallback<MatchResultUiModel>() {

    override fun areItemsTheSame(
        oldItem: MatchResultUiModel,
        newItem: MatchResultUiModel
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: MatchResultUiModel,
        newItem: MatchResultUiModel
    ): Boolean = oldItem == newItem
}
