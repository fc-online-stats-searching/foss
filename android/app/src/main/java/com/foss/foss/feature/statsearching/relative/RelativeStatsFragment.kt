package com.foss.foss.feature.statsearching.relative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.databinding.FragmentRelativeStatsBinding
import kotlinx.coroutines.launch

class RelativeStatsFragment : DiFragment() {

    override val module: Module = DefaultModule()

    private var _binding: FragmentRelativeStatsBinding? = null
    private val binding get() = _binding!!

    private val relativeStatsViewModel: RelativeStatsViewModel by activityViewModels()

    private val relativeStatsAdapter: RelativeStatsAdapter by lazy { RelativeStatsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelativeStatsBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRelativeStatsView()
        setupRelativeStatsObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setupRelativeStatsView() {
        binding.relativeRvStats.adapter = relativeStatsAdapter
    }

    private fun setupRelativeStatsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                relativeStatsViewModel.relativeStats.collect { relativeStats ->
                    relativeStatsAdapter.submitList(relativeStats)
                }
            }
        }
    }
}
