package com.foss.foss.feature.matchsearching.relative

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
import com.foss.foss.R
import com.foss.foss.databinding.FragmentRelativeMatchBinding
import com.foss.foss.feature.matchsearching.relative.detail.RelativeMatchDetailsActivity
import com.foss.foss.util.lifecycle.repeatOnStarted

class RelativeMatchFragment : DiFragment() {

    override val module: Module = DefaultModule()

    private var _binding: FragmentRelativeMatchBinding? = null
    private val binding get() = _binding!!

    private val relativeMatchViewModel: RelativeMatchViewModel by activityViewModels()

    private val relativeMatchesAdapter: RelativeMatchAdapter by lazy {
        RelativeMatchAdapter { opponentNickname, matchDetails ->
            requireContext().startActivity(
                RelativeMatchDetailsActivity.getIntent(
                    context = requireContext(),
                    opponentNickname = opponentNickname,
                    relativeMatchDetails = matchDetails
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelativeMatchBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRelativeMatchesView()
        setupRelativeMatchesUiStateObserver()
        setupRelativeMatchesEventObserver()
    }

    private fun setupRelativeMatchesView() {
        binding.relativeMatchesRv.adapter = relativeMatchesAdapter
    }

    private fun setupRelativeMatchesUiStateObserver() {
        repeatOnStarted {
            relativeMatchViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is RelativeMatchUiState.Default -> {
                        binding.relativeTvInfo.text = getString(R.string.common_request_searching_nickname)
                        binding.relativeTvInfo.isVisible = true
                        binding.relativeMatchPbLoadingBar.isVisible = false
                        relativeMatchesAdapter.submitList(emptyList())
                    }

                    is RelativeMatchUiState.Empty -> {
                        binding.relativeTvInfo.text = getString(R.string.common_empty_matches)
                        binding.relativeTvInfo.isVisible = true
                        binding.relativeMatchPbLoadingBar.isVisible = false
                        relativeMatchesAdapter.submitList(emptyList())
                    }

                    is RelativeMatchUiState.Loading -> {
                        binding.relativeMatchPbLoadingBar.isVisible = true
                        binding.relativeTvInfo.isVisible = false
                    }

                    is RelativeMatchUiState.RelativeMatches -> {
                        binding.relativeTvInfo.isVisible = false
                        binding.relativeMatchPbLoadingBar.isVisible = false
                        relativeMatchesAdapter.submitList(uiState.relativeMatches)
                    }
                }
            }
        }
    }

    private fun setupRelativeMatchesEventObserver() {
        repeatOnStarted {
            relativeMatchViewModel.event.collect { event ->
                when (event) {
                    RelativeMatchEvent.Failed -> Toast.makeText(
                        requireContext(),
                        getString(R.string.common_failed_fetching_matches),
                        Toast.LENGTH_SHORT
                    ).show()

                    RelativeMatchEvent.RefreshFailed -> Toast.makeText(
                        requireContext(),
                        getString(R.string.common_refresh_failed_message),
                        Toast.LENGTH_SHORT
                    ).show()

                    RelativeMatchEvent.RefreshSucceed -> Toast.makeText(
                        requireContext(),
                        getString(R.string.common_refresh_succeed_message),
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
