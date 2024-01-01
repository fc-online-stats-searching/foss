package com.foss.foss.feature.matchsearching.relative

import androidx.recyclerview.widget.DiffUtil
import com.foss.foss.model.RelativeMatchUiModel

class RelativeMatchDiffUtilCallBack : DiffUtil.ItemCallback<RelativeMatchUiModel>() {

    /**
     * todo: 서로 다른 두 경기의 각 경기 결과가 만약 완전히 동일한 경우 어떠한 방식으로 구분할 것인지?
     */
    override fun areItemsTheSame(
        oldItem: RelativeMatchUiModel,
        newItem: RelativeMatchUiModel
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: RelativeMatchUiModel,
        newItem: RelativeMatchUiModel
    ): Boolean = oldItem == newItem
}
