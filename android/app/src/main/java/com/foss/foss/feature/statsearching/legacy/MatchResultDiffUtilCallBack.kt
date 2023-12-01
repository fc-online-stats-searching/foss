package com.foss.foss.feature.statsearching.legacy

import androidx.recyclerview.widget.DiffUtil
import com.foss.foss.model.MatchUiModel

class MatchResultDiffUtilCallBack : DiffUtil.ItemCallback<MatchUiModel>() {

    override fun areItemsTheSame(
        oldItem: MatchUiModel,
        newItem: MatchUiModel
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: MatchUiModel,
        newItem: MatchUiModel
    ): Boolean = oldItem == newItem
}
