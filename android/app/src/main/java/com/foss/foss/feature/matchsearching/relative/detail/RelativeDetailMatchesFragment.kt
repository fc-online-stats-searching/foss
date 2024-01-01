package com.foss.foss.feature.matchsearching.relative.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.databinding.FragmentRelativeMatchesDetailBinding
import com.foss.foss.feature.matchsearching.recent.RecentMatchesAdapter
import com.foss.foss.feature.matchsearching.relative.RelativeMatchesViewModel
import com.foss.foss.util.lifecycle.repeatOnStarted

class RelativeDetailMatchesFragment : DiFragment() {
    override val module: Module by lazy { DefaultModule() }

    private var _binding: FragmentRelativeMatchesDetailBinding? = null
    private val binding
        get() = _binding!!

    private val relativeMatchesViewModel: RelativeMatchesViewModel by activityViewModels()

    private val relativeMatchesDetailAdapter: RecentMatchesAdapter by lazy {
        RecentMatchesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelativeMatchesDetailBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRelativeMatchesDetailObserver()
        setupRelativeMatchesDetailView()
    }

    override fun onResume() {
        super.onResume()

        setupData()
    }

    private fun setupData() {
        relativeMatchesViewModel.fetchRelativeMatchesBetweenUsers()
    }

    private fun setupRelativeMatchesDetailObserver() {
        repeatOnStarted {
            relativeMatchesViewModel.relativeMatchesDetails.collect {
                relativeMatchesDetailAdapter.submitList(it)
            }
        }
    }

    private fun setupRelativeMatchesDetailView() {
        binding.relativeDetailRvMatches.adapter = relativeMatchesDetailAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        relativeMatchesViewModel.resetRelativeMatchesDetails()
        _binding = null
    }
}
