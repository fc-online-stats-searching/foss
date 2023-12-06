package com.foss.foss.feature.statsearching.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.foss.foss.model.MatchUiModel

class RecentMatchesAdapter : ListAdapter<MatchUiModel, MatchViewHolder>(MatchResultDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder.valueOf(parent)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<MatchUiModel>?) {
        super.submitList(list)
    }
}
