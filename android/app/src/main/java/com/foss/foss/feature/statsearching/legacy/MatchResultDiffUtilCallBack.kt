package com.foss.foss.feature.statsearching.legacy

import androidx.recyclerview.widget.DiffUtil
import com.foss.foss.model.MatchResultUiModel

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
