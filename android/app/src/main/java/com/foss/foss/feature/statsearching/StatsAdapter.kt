package com.foss.foss.feature.statsearching

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.foss.foss.model.MatchResultUiModel

class StatsAdapter :
    ListAdapter<MatchResultUiModel, MatchResultViewHolder>(MatchResultDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchResultViewHolder {
        return MatchResultViewHolder.valueOf(parent)
    }

    override fun onBindViewHolder(holder: MatchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<MatchResultUiModel>?) {
        super.submitList(list)
    }
}
