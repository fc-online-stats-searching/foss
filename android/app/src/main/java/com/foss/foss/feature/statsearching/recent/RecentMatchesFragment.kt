package com.foss.foss.feature.statsearching.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.R
import com.foss.foss.databinding.FragmentRecentMatchesBinding

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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecentMatchesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObserver()
    }

    private fun setupView() {
        binding.recentMatchRvMatches.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.matches.observe(viewLifecycleOwner) { matches ->
            adapter.submitList(matches)
        }
        viewModel.matchTypes.observe(viewLifecycleOwner) { matchTypes ->
            binding.recentMatchSpinnerMatchType.adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item_match_type,
                matchTypes.map { matchType ->
                    getString(matchType.resId)
                }.toTypedArray(),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
