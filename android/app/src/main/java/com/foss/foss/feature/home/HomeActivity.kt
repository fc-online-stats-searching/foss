package com.foss.foss.feature.home

import android.os.Bundle
import androidx.fragment.app.commit
import com.boogiwoogi.woogidi.activity.DiActivity
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.boogiwoogi.woogidi.viewmodel.diViewModels
import com.foss.foss.R
import com.foss.foss.databinding.ActivityHomeBinding
import com.foss.foss.feature.matchsearching.recent.RecentMatchesFragment
import com.foss.foss.feature.matchsearching.recent.RecentMatchesViewModel
import com.foss.foss.feature.matchsearching.relative.RelativeMatchesFragment
import com.foss.foss.feature.matchsearching.relative.RelativeMatchesViewModel
import com.foss.foss.util.OnChangeVisibilityListener
import com.foss.foss.util.lifecycle.repeatOnStarted

class HomeActivity : DiActivity(), OnChangeVisibilityListener {

    private lateinit var binding: ActivityHomeBinding

    override val module: Module by lazy { DefaultModule() }

    private val recentMatchesViewModel: RecentMatchesViewModel by diViewModels()
    private val relativeMatchesViewModel: RelativeMatchesViewModel by diViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupHomeView()
        setupRecentMatchesObserver()
        setupRelativeMatchesObserver()
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
                R.id.item_recent_matches -> {
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_match, RecentMatchesFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.item_relative_matches -> {
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_match, RelativeMatchesFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
        binding.homeBnvMenu.selectedItemId = R.id.item_recent_matches
    }

    private fun setupRecentMatchesObserver() {
        repeatOnStarted {
            recentMatchesViewModel.uiState.collect {
            }
        }
    }

    private fun setupRelativeMatchesObserver() {
        repeatOnStarted {
            relativeMatchesViewModel.relativeMatches.collect {
            }
        }
    }

    private fun setSearchingMatchesButtonClickListener(id: Int) {
        when (id) {
            R.id.item_recent_matches -> setSearchingRecentMatchesButtonClickListener()
            R.id.item_relative_matches -> setSearchingRelativeMatchesButtonClickListener()
        }
    }

    private fun setSearchingRecentMatchesButtonClickListener() {
        binding.homeIvFossLogo.setOnClickListener {
            recentMatchesViewModel.fetchMatches(binding.homeEtNicknameSearching.text.toString())
        }
    }

    private fun setSearchingRelativeMatchesButtonClickListener() {
        binding.homeIvFossLogo.setOnClickListener {
            relativeMatchesViewModel.fetchRelativeMatches(binding.homeEtNicknameSearching.text.toString())
            onChangeVisibility()
        }
    }

    override fun onChangeVisibility() {
        val fragment = supportFragmentManager.findFragmentById(R.id.home_fcv_match)

        when (fragment) {
            is RecentMatchesFragment -> {
                fragment.changeVisibility()
            }

            is RelativeMatchesFragment -> {
                fragment.changeVisibility()
            }
        }
    }
}
