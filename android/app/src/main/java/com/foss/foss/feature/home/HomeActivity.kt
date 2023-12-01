package com.foss.foss.feature.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.foss.foss.R
import com.foss.foss.databinding.ActivityHomeBinding
import com.foss.foss.feature.statsearching.recent.RecentMatchesFragment
import com.foss.foss.feature.statsearching.relative.RelativeStatsFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupView()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    private fun setupView() {
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.homeBnvMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_recent_stats -> {
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_stats, RecentMatchesFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.item_relative_stats -> {
                    supportFragmentManager.commit {
                        replace(R.id.home_fcv_stats, RelativeStatsFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
        binding.homeBnvMenu.selectedItemId = R.id.item_recent_stats
    }
}
