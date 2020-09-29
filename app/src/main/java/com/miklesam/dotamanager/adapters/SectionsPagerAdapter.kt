package com.miklesam.dotamanager.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.miklesam.dotamanager.simplefragments.PositionsPager

class SectionsPagerAdapter(fm: FragmentManager,val listener:OnPlayerChooseListener) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_TITLES = arrayOf(
        "position 1",
        "position 2",
        "position 3",
        "position 4",
        "position 5"
    )

    override fun getItem(position: Int): Fragment {
        return PositionsPager(position + 1,listener)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 5
    }
}