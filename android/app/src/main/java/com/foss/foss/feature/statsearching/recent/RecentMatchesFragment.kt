package com.foss.foss.feature.statsearching.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_recent_matches,
            container,
            false
        )

        return binding.root
    }
}
