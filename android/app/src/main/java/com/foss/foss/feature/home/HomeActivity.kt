package com.foss.foss.feature.home

import android.os.Bundle
import android.widget.ArrayAdapter
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
import com.foss.foss.util.OnChangeVisibilityListener

class HomeActivity : DiActivity(), OnChangeVisibilityListener {

    private lateinit var binding: ActivityHomeBinding

    override val module: Module by lazy { DefaultModule() }

    private val recentMatchViewModel: RecentMatchViewModel by diViewModels()
    private val relativeMatchViewModel: RelativeMatchViewModel by diViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupHomeView()
        setSearchingMatchesButtonClickListener()
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
        recentMatchViewModel.fetchEmptyMatches()
        binding.homeBnvMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_recent_matches -> {
                    binding.homeSpinnerMatchType.isVisible = true
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_match, RecentMatchFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.item_relative_matches -> {
                    binding.homeSpinnerMatchType.isVisible = false
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
        binding.homeSpinnerMatchType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            MatchTypeUiModel.values().map { getString(it.resId) }
        )
    }

    private fun setSearchingMatchesButtonClickListener() {
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

    override fun onChangeVisibility() {
        val fragment = supportFragmentManager.findFragmentById(R.id.home_fcv_match)

        when (fragment) {
            is RecentMatchFragment -> {
                fragment.changeVisibility()
            }

            is RelativeMatchFragment -> {
                fragment.changeVisibility()
            }
        }
    }
}
