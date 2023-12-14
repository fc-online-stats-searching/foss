package com.foss.foss.feature.statsearching.relative

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.foss.foss.model.RelativeStatUiModel

class RelativeStatsAdapter :
    ListAdapter<RelativeStatUiModel, RelativeStatsViewHolder>(RelativeStatDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelativeStatsViewHolder {
        return RelativeStatsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RelativeStatsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<RelativeStatUiModel>?) {
        super.submitList(list)
    }
}
