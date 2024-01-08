package com.foss.foss.feature.matchsearching.relative.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.databinding.FragmentRelativeMatchDetailBinding
import com.foss.foss.feature.matchsearching.recent.RecentMatchAdapter
import com.foss.foss.feature.matchsearching.relative.RelativeMatchViewModel
import com.foss.foss.util.lifecycle.repeatOnStarted

class RelativeDetailMatchFragment : DiFragment() {

    override val module: Module by lazy { DefaultModule() }

    private var _binding: FragmentRelativeMatchDetailBinding? = null
    private val binding
        get() = _binding!!

    private val relativeMatchViewModel: RelativeMatchViewModel by activityViewModels()

    private val relativeMatchesDetailAdapter: RecentMatchAdapter by lazy {
        RecentMatchAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelativeMatchDetailBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRelativeMatchesDetailObserver()
        setupRelativeMatchesDetailView()
    }

    override fun onResume() {
        super.onResume()

        fetchRelativeMatchesDetail()
    }

    private fun fetchRelativeMatchesDetail() {
        relativeMatchViewModel.fetchRelativeMatchesBetweenUsers()
    }

    private fun setupRelativeMatchesDetailObserver() {
        repeatOnStarted {
            relativeMatchViewModel.relativeMatchesDetails.collect {
                relativeMatchesDetailAdapter.submitList(it)
            }
        }
    }

    private fun setupRelativeMatchesDetailView() {
        binding.relativeDetailRvMatches.adapter = relativeMatchesDetailAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        relativeMatchViewModel.resetRelativeMatchesDetails()
        _binding = null
    }
}
