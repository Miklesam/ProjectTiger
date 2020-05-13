package com.miklesam.dotamanager.ui.closedquali

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import kotlinx.android.synthetic.main.fragment_closed_quali.*
import kotlinx.android.synthetic.main.group_stage_layout.view.*

class ClosedQuali :Fragment(R.layout.fragment_closed_quali){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupLayoutA.Team1Name.text="Virtus Pro"
        groupLayoutB.Team1Name.text="NaVi"
    }
}