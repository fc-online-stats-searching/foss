package com.foss.foss.feature.matchsearching.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.databinding.FragmentRecentMatchBinding
import com.foss.foss.util.lifecycle.repeatOnStarted

class RecentMatchFragment : DiFragment() {

    override val module: Module by lazy { DefaultModule() }

    private var _binding: FragmentRecentMatchBinding? = null
    private val binding: FragmentRecentMatchBinding
        get() = _binding!!

    private val viewModel: RecentMatchViewModel by activityViewModels()

    private val adapter: RecentMatchAdapter by lazy { RecentMatchAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentMatchBinding.inflate(layoutInflater)

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
                    is RecentMatchUiState.Empty -> {
                        binding.recentTvInfo.isVisible = true
                        binding.recentMatchPbLoadingBar.isVisible = false
                    }

                    is RecentMatchUiState.Loading -> {
                        binding.recentTvInfo.isVisible = false
                        binding.recentMatchPbLoadingBar.isVisible = true
                    }

                    is RecentMatchUiState.RecentMatch -> {
                        binding.recentTvInfo.isVisible = false
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
                    RecentMatchEvent.Failed -> {
                        binding.recentMatchPbLoadingBar.isVisible = false
                    }

                    RecentMatchEvent.RefreshFailed -> Toast.makeText(
                        requireContext(),
                        "전적 갱신에 실패했습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
