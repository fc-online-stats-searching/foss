package com.foss.foss.feature.home

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import com.boogiwoogi.woogidi.activity.DiActivity
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.boogiwoogi.woogidi.viewmodel.diViewModels
import com.foss.foss.R
import com.foss.foss.databinding.ActivityHomeBinding
import com.foss.foss.feature.matchsearching.recent.RecentMatchFragment
import com.foss.foss.feature.matchsearching.recent.RecentMatchViewModel
import com.foss.foss.feature.matchsearching.relative.RelativeMatchFragment
import com.foss.foss.feature.matchsearching.relative.RelativeMatchViewModel
import com.foss.foss.model.MatchTypeUiModel

class HomeActivity : DiActivity() {

    private lateinit var binding: ActivityHomeBinding

    override val module: Module by lazy { DefaultModule() }

    private val recentMatchViewModel: RecentMatchViewModel by diViewModels()
    private val relativeMatchViewModel: RelativeMatchViewModel by diViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupHomeView()
        setupSearchingMatchesButtonClickListener()
    }

    private fun setupBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    private fun setupHomeView() {
        setupBottomNavigationView()
        setupMatchTypeSpinnerAdapter()
    }

    private fun setupBottomNavigationView() {
        /**
         * todo: 현재 woogi-di를 사용함으로써 발생하는 문제를 해결하기 위한 코드
         */
        relativeMatchViewModel.fetchDefaultRelativeMatches()
        recentMatchViewModel.fetchDefaultMatches()
        binding.homeBnvMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_recent_matches -> {
                    binding.homeSpinnerMatchType.isVisible = true
                    binding.homeTvRefresh.setOnClickListener {
                        recentMatchViewModel.refreshMatches(binding.homeEtNicknameSearching.text.toString())
                    }
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_match, RecentMatchFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.item_relative_matches -> {
                    binding.homeSpinnerMatchType.isVisible = false
                    binding.homeTvRefresh.setOnClickListener {
                        relativeMatchViewModel.refreshMatches(binding.homeEtNicknameSearching.text.toString())
                    }
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_match, RelativeMatchFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
        binding.homeBnvMenu.selectedItemId = R.id.item_recent_matches
    }

    private fun setupMatchTypeSpinnerAdapter() {
        with(binding.homeSpinnerMatchType) {
            adapter = ArrayAdapter(
                this@HomeActivity,
                R.layout.custom_spinner_item_match_type,
                MatchTypeUiModel.values().map { getString(it.resId) }
            )
            viewTreeObserver.addOnGlobalLayoutListener {
                (selectedView as TextView).setBackgroundResource(R.drawable.custom_match_type_spinner_background)
            }
        }
    }

    private fun setupSearchingMatchesButtonClickListener() {
        with(binding) {
            homeIvFossLogo.setOnClickListener {
                recentMatchViewModel.fetchMatches(
                    homeEtNicknameSearching.text.toString(),
                    MatchTypeUiModel.values()[homeSpinnerMatchType.selectedItemPosition]
                )
                relativeMatchViewModel.fetchRelativeMatches(homeEtNicknameSearching.text.toString())
            }
        }
    }
}
