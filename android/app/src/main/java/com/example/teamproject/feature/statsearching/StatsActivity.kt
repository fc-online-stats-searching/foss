package com.example.teamproject.feature.statsearching

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.teamproject.R
import com.example.teamproject.databinding.ActivityStatsBinding
import com.example.teamproject.di.ViewModelFactoryProvider

class StatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatsBinding

    private val adapter: StatsAdapter by lazy {
        StatsAdapter()
    }
    private val viewModel: StatsViewModel by viewModels {
        ViewModelFactoryProvider.statsViewModelFactory
    }

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
