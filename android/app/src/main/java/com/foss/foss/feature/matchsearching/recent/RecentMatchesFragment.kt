package com.foss.foss.feature.matchsearching.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.databinding.FragmentRecentMatchesBinding
import com.foss.foss.util.lifecycle.repeatOnStarted

class RecentMatchesFragment : DiFragment() {

    override val module: Module by lazy { DefaultModule() }

    private var _binding: FragmentRecentMatchesBinding? = null
    private val binding: FragmentRecentMatchesBinding
        get() = _binding!!

    private val viewModel: RecentMatchesViewModel by activityViewModels()

    private val adapter: RecentMatchesAdapter by lazy { RecentMatchesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentMatchesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecentMatchesView()
        setupRecentMatchesUiStateObserver()
        setupRecentMatchesEventObserver()
    }

    private fun setupRecentMatchesView() {
        binding.recentMatchRvMatches.adapter = adapter
    }

    private fun setupRecentMatchesUiStateObserver() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is RecentMatchesUiState.Empty -> {
                    }

                    is RecentMatchesUiState.Loading -> {
                        binding.recentMatchPbLoadingBar.isVisible = true
                    }

                    is RecentMatchesUiState.RecentMatches -> {
                        binding.recentMatchPbLoadingBar.isVisible = false
                        adapter.submitList(uiState.matches)
                    }
                }
            }
        }
    }

    private fun setupRecentMatchesEventObserver() {
        repeatOnStarted {
            viewModel.event.collect { event ->
                when (event) {
                    RecentMatchesEvent.Failed -> {
                        binding.recentMatchPbLoadingBar.isVisible = false
                    }
                }
            }
        }
    }

    fun changeVisibility() {
        if (binding.recentTvInfo.isVisible) {
            binding.recentTvInfo.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
