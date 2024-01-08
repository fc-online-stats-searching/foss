package com.foss.foss.feature.matchsearching.relative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.R
import com.foss.foss.databinding.FragmentRelativeMatchBinding
import com.foss.foss.feature.matchsearching.relative.detail.RelativeDetailMatchFragment
import com.foss.foss.util.lifecycle.repeatOnStarted

class RelativeMatchFragment : DiFragment() {

    override val module: Module = DefaultModule()

    private var _binding: FragmentRelativeMatchBinding? = null
    private val binding get() = _binding!!

    private val relativeMatchViewModel: RelativeMatchViewModel by activityViewModels()

    private val relativeMatchesAdapter: RelativeMatchAdapter by lazy {
        RelativeMatchAdapter { opponentName ->
            relativeMatchViewModel.updateOpponentName(opponentName)
            parentFragmentManager.commit {
                replace<RelativeDetailMatchFragment>(R.id.home_fcv_match)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
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
            relativeMatchViewModel.relativeMatches.collect { relativeMatches ->
                relativeMatchesAdapter.submitList(relativeMatches)
            }
        }
    }

    private fun setupRelativeMatchesEventObserver() {
        repeatOnStarted {
            relativeMatchViewModel.event.collect { event ->
                when (event) {
                    RelativeMatchEvent.Failed -> Toast.makeText(
                        requireContext(),
                        getString(R.string.relative_matches_failed_fetching_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun changeVisibility() {
        if (binding.relativeTvInfo.isVisible) {
            binding.relativeTvInfo.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
