package com.miklesam.dotamanager.adapters

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.simplefragments.PositionsPager

@SuppressLint("WrongConstant")
class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_TITLES = arrayOf(
        "position 1",
        "position 2",
        "position 3",
        "position 4",
        "position 5"
    )

    override fun getItem(position: Int): Fragment {
        return PositionsPager(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 5
    }
}