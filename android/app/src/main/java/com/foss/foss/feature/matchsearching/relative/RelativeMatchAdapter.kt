package com.foss.foss.feature.matchsearching.relative

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.foss.foss.model.RelativeMatchUiModel

class RelativeMatchAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<RelativeMatchUiModel, RelativeMatchViewHolder>(
    RelativeMatchDiffUtilCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelativeMatchViewHolder {
        return RelativeMatchViewHolder.from(parent) {
            onClick(getItem(it).opponentName)
        }
    }

    override fun onBindViewHolder(holder: RelativeMatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<RelativeMatchUiModel>?) {
        super.submitList(list)
    }
}
