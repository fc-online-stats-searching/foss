package com.foss.foss.feature.home

import android.os.Bundle
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
import com.foss.foss.feature.statsearching.relative.RelativeStatsViewModel
import com.foss.foss.util.OnChangeVisibilityListener
import com.foss.foss.util.lifecycle.repeatOnStarted

class HomeActivity : DiActivity(), OnChangeVisibilityListener {

    private lateinit var binding: ActivityHomeBinding

    override val module: Module by lazy { DefaultModule() }

    private val recentMatchesViewModel: RecentMatchesViewModel by diViewModels()
    private val relativeStatsViewModel: RelativeStatsViewModel by diViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupHomeView()
        setupRecentMatchesObserver()
        setupRelativeStatsObserver()
        setSearchingRecentMatchesButtonClickListener()
    }

    private fun setupBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    private fun setupHomeView() {
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.homeBnvMenu.setOnItemSelectedListener { item ->
            setSearchingMatchesButtonClickListener(item.itemId)

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

    private fun setupRecentMatchesObserver() {
        recentMatchesViewModel.matches.observe(this) {
        }
    }

    private fun setupRelativeStatsObserver() {
        repeatOnStarted {
            relativeStatsViewModel.relativeStats.collect {
            }
        }
    }

    private fun setSearchingMatchesButtonClickListener(id: Int) {
        when (id) {
            R.id.item_recent_stats -> setSearchingRecentMatchesButtonClickListener()
            R.id.item_relative_stats -> setSearchingRelativeStatsButtonClickListener()
        }
    }

    private fun setSearchingRecentMatchesButtonClickListener() {
        binding.homeIvFossLogo.setOnClickListener {
            recentMatchesViewModel.fetchMatches(binding.homeEtNicknameSearching.text.toString())
            onChangeVisibility()
        }
    }

    private fun setSearchingRelativeStatsButtonClickListener() {
        binding.homeIvFossLogo.setOnClickListener {
            relativeStatsViewModel.fetchRelativeStats(binding.homeEtNicknameSearching.text.toString())
            onChangeVisibility()
        }
    }

    override fun onChangeVisibility() {
        val fragment = supportFragmentManager.findFragmentById(R.id.home_fcv_stats)
        when (fragment) {
            is RecentMatchesFragment -> fragment.changeVisibility()
            is RelativeStatsFragment -> fragment.changeVisibility()
        }
    }
}
