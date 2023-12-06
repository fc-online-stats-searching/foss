package com.foss.foss.feature.statsearching.relative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foss.foss.databinding.FragmentRelativeStatsBinding
import com.foss.foss.model.RelativeStatsUiModel.Companion.mockDatas

class RelativeStatsFragment : Fragment() {
    private var _binding: FragmentRelativeStatsBinding? = null
    private val binding get() = _binding!!
    private val relativeStatsAdapter: RelativeStatsAdapter by lazy { RelativeStatsAdapter(mockDatas) }

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
        binding.relativeRvStats.adapter = relativeStatsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
