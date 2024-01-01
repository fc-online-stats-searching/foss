package com.foss.foss.feature.matchsearching.recent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.databinding.FragmentRecentMatchesBinding
import com.foss.foss.util.lifecycle.repeatOnStarted

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
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is RecentMatchesUiState.Default -> {
                        Log.d("hello", "setupObserver: default")
                        binding.recentMatchSpinnerMatchType.adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            uiState.matchTypes
                        )
                    }

                    is RecentMatchesUiState.Loading -> {
                    }

                    is RecentMatchesUiState.Success -> {
                        Log.d("hello", "setupObserver: success")
                        adapter.submitList(uiState.data)
                    }
                }
            }
        }
    }

    fun changeVisibility() {
        if (binding.recentTvInfo.isVisible) {
            binding.recentTvInfo.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}