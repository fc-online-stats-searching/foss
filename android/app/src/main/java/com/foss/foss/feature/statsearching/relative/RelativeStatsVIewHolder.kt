package com.foss.foss.feature.statsearching.relative

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foss.foss.R
import com.foss.foss.databinding.ItemRelativeStatsBinding
import com.foss.foss.model.RelativeStatsUiModel

class RelativeStatsVIewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_relative_stats, parent, false),
) {
    private val binding = ItemRelativeStatsBinding.bind(itemView)

    fun bind(data: RelativeStatsUiModel) {
        binding.relativeStatsUiModel = data
    }
}
