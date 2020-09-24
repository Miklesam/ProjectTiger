package com.miklesam.dotamanager.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.simplefragments.FragmentMenu
import com.miklesam.dotamanager.ui.choosePlayers.FragmentChoosePlayers

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val TAB_TITLES = arrayOf(
        R.string.pos1,
        R.string.pos2,
        R.string.pos3,
        R.string.pos4,
        R.string.pos5
    )

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        var fragment: Fragment? = null
        when (position) {
            //0 -> fragment = LatestTab()
            //1 -> fragment = HotTab()
            //2 -> fragment = TopTab()
        }
        return FragmentChoosePlayers()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 5
    }
}