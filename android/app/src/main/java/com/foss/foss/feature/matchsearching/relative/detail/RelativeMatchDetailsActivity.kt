package com.foss.foss.feature.matchsearching.relative.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foss.foss.R
import com.foss.foss.databinding.ActivityRelativeMatchDetailsBinding
import com.foss.foss.feature.matchsearching.recent.RecentMatchAdapter
import com.foss.foss.model.MatchUiModel
import com.foss.foss.util.putParcelableArrayListCompat

class RelativeMatchDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelativeMatchDetailsBinding

    private val relativeMatchDetailsAdapter: RecentMatchAdapter by lazy { RecentMatchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRelativeMatchDetailsBinding()
        setupRelativeMatchDetailsView()
        setupRelativeMatchDetailsButtonListener()
    }

    private fun setupRelativeMatchDetailsBinding() {
        binding = ActivityRelativeMatchDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    private fun setupRelativeMatchDetailsView() {
        binding.relativeMatchDetailsRvMatches.adapter = relativeMatchDetailsAdapter
        binding.relativeMatchDetailsTvTitle.text =
            getString(R.string.relative_match_details_title).format(
                intent.getStringExtra(OPPONENT_NICKNAME_KEY)
            )
        relativeMatchDetailsAdapter.submitList(intent.putParcelableArrayListCompat(RELATIVE_DETAILS_KEY))
    }

    private fun setupRelativeMatchDetailsButtonListener() {
        binding.relativeMatchDetailsIbBack.setOnClickListener { finish() }
    }

    companion object {

        private const val RELATIVE_DETAILS_KEY = "relative_details"
        private const val OPPONENT_NICKNAME_KEY = "opponent_nickname"

        fun start(
            context: Context,
            opponentNickname: String,
            relativeMatchDetails: ArrayList<MatchUiModel>
        ) {
            val intent = Intent(context, RelativeMatchDetailsActivity::class.java).apply {
                putExtra(OPPONENT_NICKNAME_KEY, opponentNickname)
                putParcelableArrayListExtra(RELATIVE_DETAILS_KEY, relativeMatchDetails)
            }

            context.startActivity(intent)
        }
    }
}
