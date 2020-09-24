package com.miklesam.dotamanager.simplefragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.SectionsPagerAdapter
import kotlinx.android.synthetic.main.fragment_position_pager.*


class PositionsPager : Fragment(R.layout.fragment_position_pager) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), parentFragmentManager)
        pager_positions.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(pager_positions)
    }
}