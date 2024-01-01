package com.foss.foss.feature.matchearching.recent

import androidx.recyclerview.widget.DiffUtil
import com.foss.foss.model.MatchUiModel

class MatchDiffUtilCallBack : DiffUtil.ItemCallback<MatchUiModel>() {

    /**
     * todo: 서로 다른 두 경기의 각 경기 결과가 만약 완전히 동일한 경우 어떠한 방식으로 구분할 것인지?
     */
    override fun areItemsTheSame(
        oldItem: MatchUiModel,
        newItem: MatchUiModel
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: MatchUiModel,
        newItem: MatchUiModel
    ): Boolean = oldItem == newItem
}
