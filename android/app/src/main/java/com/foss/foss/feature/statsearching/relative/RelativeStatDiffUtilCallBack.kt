package com.foss.foss.feature.statsearching.relative

import androidx.recyclerview.widget.DiffUtil
import com.foss.foss.model.RelativeStatUiModel

class RelativeStatDiffUtilCallBack : DiffUtil.ItemCallback<RelativeStatUiModel>() {

    /**
     * todo: 서로 다른 두 경기의 각 경기 결과가 만약 완전히 동일한 경우 어떠한 방식으로 구분할 것인지?
     */
    override fun areItemsTheSame(
        oldItem: RelativeStatUiModel,
        newItem: RelativeStatUiModel
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: RelativeStatUiModel,
        newItem: RelativeStatUiModel
    ): Boolean = oldItem == newItem
}