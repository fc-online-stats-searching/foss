package com.foss.foss.feature.statsearching.relative.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.databinding.FragmentRelativeDetailStatsBinding
import com.foss.foss.feature.statsearching.recent.RecentMatchesAdapter
import com.foss.foss.feature.statsearching.relative.RelativeStatsFragment.Companion.KEY_NICKNAME
import com.foss.foss.feature.statsearching.relative.RelativeStatsFragment.Companion.KEY_OPPONENT_NAME
import com.foss.foss.feature.statsearching.relative.RelativeStatsFragment.Companion.KEY_REQUEST
import com.foss.foss.feature.statsearching.relative.RelativeStatsViewModel
import kotlinx.coroutines.launch

class RelativeDetailStatsFragment : DiFragment() {
    override val module: Module by lazy { DefaultModule() }

    private var _binding: FragmentRelativeDetailStatsBinding? = null
    private val binding
        get() = _binding!!

    private val relativeStatsViewModel: RelativeStatsViewModel by activityViewModels()
    private val relativeDetailStatsAdapter: RecentMatchesAdapter by lazy {
        RecentMatchesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRelativeDetailStatsBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRelativeDetailStatsObserver()
        setupRelativeDetailStatsView()
    }

    override fun onResume() {
        super.onResume()

        setupData()
    }

    private fun setupData() {
        var nickname = ""
        var opponentName = ""
        setFragmentResultListener(KEY_REQUEST) { _, bundle ->
            nickname = requireNotNull(bundle.getString(KEY_NICKNAME))
            opponentName = requireNotNull(bundle.getString(KEY_OPPONENT_NAME))
        }
        relativeStatsViewModel.fetchMatchesBetweenUsers(nickname, opponentName)
    }

    private fun setupRelativeDetailStatsObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                relativeStatsViewModel.relativeStatsDetails.collect {
                    relativeDetailStatsAdapter.submitList(it)
                }
            }
        }
    }

    private fun setupRelativeDetailStatsView() {
        binding.relativeDetailRvMatches.adapter = relativeDetailStatsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
