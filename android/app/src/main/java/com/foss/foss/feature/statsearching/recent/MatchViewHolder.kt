package com.foss.foss.feature.statsearching.recent

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foss.foss.R
import com.foss.foss.databinding.ItemMatchBinding
import com.foss.foss.model.MatchUiModel
import java.time.format.DateTimeFormatter

class MatchViewHolder(
    private val binding: ItemMatchBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(match: MatchUiModel) {
        with(binding) {
            this.match = match
            itemMatchTvDate.text = match.date.format(
                DateTimeFormatter.ofPattern(
                    root.context.getString(
                        R.string.common_date_format
                    )
                )
            )
            itemMatchTvScore.text = root.context.getString(
                R.string.common_score_format,
                match.point,
                match.otherPoint
            )
            itemMatchViewSideBar.setBackgroundColor(getSideBarColor(match.winDrawLose))
            itemMatchIvArrowDown.setColorFilter(getDropDownArrowColor(match.winDrawLose))
            itemMatchTvMatchType.setTextColor(getTextColor(match.winDrawLose))
            itemMatchTvWinDrawLose.setTextColor(getTextColor(match.winDrawLose))
        }
    }

    /**
     * todo: 색깔 때문에 브랜치 충돌이 발생할까봐 현재 추가적인 color.xml 정의하지 않음.
     */
    private fun getSideBarColor(winDrawLose: String): Int = when (winDrawLose) {
        "승리" -> Color.parseColor("#D5E3FE")
        "무승부" -> Color.LTGRAY
        "패배" -> Color.parseColor("#FFD7D9")
        else -> throw IllegalArgumentException()
    }

    private fun getTextColor(winDrawLose: String): Int = when (winDrawLose) {
        "승리" -> Color.parseColor("#4171D6")
        "무승부" -> Color.LTGRAY
        "패배" -> Color.parseColor("#E9455C")
        else -> throw IllegalArgumentException()
    }

    private fun getDropDownArrowColor(winDrawLose: String): Int = when (winDrawLose) {
        "승리" -> Color.parseColor("#5383E8")
        "무승부" -> Color.DKGRAY
        "패배" -> Color.parseColor("#E9455C")
        else -> throw IllegalArgumentException()
    }

    companion object {

        fun valueOf(parent: ViewGroup): MatchViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMatchBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return MatchViewHolder(binding)
        }
    }
}
