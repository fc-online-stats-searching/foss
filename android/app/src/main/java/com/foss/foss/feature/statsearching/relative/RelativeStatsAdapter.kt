package com.foss.foss.feature.statsearching.relative

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foss.foss.model.RelativeStatsUiModel

class RelativeStatsAdapter(private val relativeStats: List<RelativeStatsUiModel>) :
    RecyclerView.Adapter<RelativeStatsVIewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelativeStatsVIewHolder {
        return RelativeStatsVIewHolder(parent)
    }

    override fun onBindViewHolder(holder: RelativeStatsVIewHolder, position: Int) {
        holder.bind(relativeStats[position])
    }

    override fun getItemCount(): Int {
        return relativeStats.size
    }
}
