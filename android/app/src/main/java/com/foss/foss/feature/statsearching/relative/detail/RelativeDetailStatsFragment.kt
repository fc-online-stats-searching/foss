package com.foss.foss.feature.statsearching.relative.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.boogiwoogi.woogidi.fragment.DiFragment
import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Module
import com.foss.foss.databinding.FragmentRelativeDetailStatsBinding
import com.foss.foss.feature.statsearching.relative.RelativeStatsFragment.Companion.BUNDLE_KEY
import com.foss.foss.feature.statsearching.relative.RelativeStatsFragment.Companion.REQUEST_KEY

class RelativeDetailStatsFragment : DiFragment() {
    override val module: Module by lazy { DefaultModule() }

    private var _binding: FragmentRelativeDetailStatsBinding? = null
    private val binding
        get() = _binding!!

    private val relativeDetailStatsViewModel: RelativeDetailStatsViewModel by activityViewModels()
    private lateinit var opponentName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRelativeDetailStatsBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            opponentName = requireNotNull(bundle.getString(BUNDLE_KEY))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}
