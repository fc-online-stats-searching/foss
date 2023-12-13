package com.foss.foss.feature.statsearching.relative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.R
import com.foss.foss.databinding.FragmentRelativeStatsBinding
import com.foss.foss.util.lifecycle.repeatOnStarted
import com.foss.foss.feature.statsearching.recent.RecentMatchesFragment
import kotlinx.coroutines.launch

class RelativeStatsFragment : DiFragment() {

    override val module: Module = DefaultModule()

    private var _binding: FragmentRelativeStatsBinding? = null
    private val binding get() = _binding!!

    private val relativeStatsViewModel: RelativeStatsViewModel by activityViewModels()

    private val relativeStatsAdapter: RelativeStatsAdapter by lazy {
        RelativeStatsAdapter {
            parentFragmentManager.commit {
                replace<RecentMatchesFragment>(R.id.home_fcv_stats)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRelativeStatsBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRelativeStatsView()
        setupRelativeStatsObserver()
        setupRelativeStatsEventObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setupRelativeStatsView() {
        binding.relativeRvStats.adapter = relativeStatsAdapter
    }

    private fun setupRelativeStatsObserver() {
        repeatOnStarted {
            relativeStatsViewModel.relativeStats.collect { relativeStats ->
                relativeStatsAdapter.submitList(relativeStats)
            }
        }
    }

    private fun setupRelativeStatsEventObserver() {
        repeatOnStarted {
            relativeStatsViewModel.event.collect { event ->
                when (event) {
                    RelativeStatsEvent.Failed -> Toast.makeText(
                        requireContext(),
                        getString(R.string.relative_stats_failed_fetching_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
