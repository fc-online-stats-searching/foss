package com.foss.foss.feature.statsearching.relative

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foss.foss.model.RelativeStatsUiModel

class RelativeStatsAdapter(private val relativeStats: List<RelativeStatsUiModel>) :
    RecyclerView.Adapter<RelativeStatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelativeStatsViewHolder {
        return RelativeStatsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RelativeStatsViewHolder, position: Int) {
        holder.bind(relativeStats[position])
    }

    override fun getItemCount(): Int {
        return relativeStats.size
    }
}
