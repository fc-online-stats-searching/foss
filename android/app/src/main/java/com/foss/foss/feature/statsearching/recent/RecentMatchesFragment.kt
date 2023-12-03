package com.foss.foss.feature.statsearching.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentRecentMatchesBinding>(
            layoutInflater,
            R.layout.fragment_recent_matches,
            container,
            false
        ).also {
            it.lifecycleOwner = viewLifecycleOwner
        }

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
        viewModel.matches.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
