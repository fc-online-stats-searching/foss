package com.foss.foss.feature.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.boogiwoogi.woogidi.activity.DiActivity
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.boogiwoogi.woogidi.viewmodel.diViewModels
import com.foss.foss.R
import com.foss.foss.databinding.ActivityHomeBinding
import com.foss.foss.feature.statsearching.recent.RecentMatchesFragment
import com.foss.foss.feature.statsearching.recent.RecentMatchesViewModel
import com.foss.foss.feature.statsearching.relative.RelativeStatsFragment

class HomeActivity : DiActivity() {

    private lateinit var binding: ActivityHomeBinding

    override val module: Module by lazy { DefaultModule() }

    private val recentMatchesViewModel: RecentMatchesViewModel by diViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupView()
        setupButtonClickListener()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        ).also {
            it.viewModel = recentMatchesViewModel
        }
    }

    private fun setupView() {
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.homeBnvMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_recent_stats -> {
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_stats, RecentMatchesFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.item_relative_stats -> {
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_stats, RelativeStatsFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
        binding.homeBnvMenu.selectedItemId = R.id.item_recent_stats
    }

    private fun setupButtonClickListener() {
        binding.homeIvFossLogo.setOnClickListener {
            recentMatchesViewModel.fetchMatches(binding.homeEtNicknameSearching.text.toString())
        }
    }
}
