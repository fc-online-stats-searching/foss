package com.foss.foss.feature.statsearching

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.boogiwoogi.woogidi.activity.DiActivity
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.di.woogidi.viewmodel.diViewModels
import com.foss.foss.R
import com.foss.foss.databinding.ActivityStatsBinding

class StatsActivity : DiActivity() {

    private lateinit var binding: ActivityStatsBinding

    override val module: Module by lazy { DefaultModule() }

    private val adapter: StatsAdapter by lazy { StatsAdapter() }

    private val viewModel: StatsViewModel by diViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupView()
        setupStateObserver()
        setupEventObserver()
    }

    private fun setupBinding() {
        binding = DataBindingUtil
            .setContentView<ActivityStatsBinding>(this, R.layout.activity_stats)
            .also {
                it.lifecycleOwner = this
            }
    }

    private fun setupView() {
        with(binding) {
            statsRvStats.adapter = adapter
            statsButtonSearch.setOnClickListener {
                viewModel.searchRecentlyStats(statsEtNickname.text.toString())
            }
        }
    }

    private fun setupStateObserver() {
        viewModel.uiState.observe(this) {
            when (it) {
                is StatsUiState.Matches -> adapter.submitList(it.matchesResult)

                is StatsUiState.Loading -> {}
            }
        }
    }

    private fun setupEventObserver() {
        viewModel.event.observe(this) {
            when (it) {
                is StatsEvent.Failed -> Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
