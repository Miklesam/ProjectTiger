package com.miklesam.bestdotamanager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_menu.*

class FragmentMenu :Fragment(R.layout.fragment_menu){

    interface menuListener {
        fun gameClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuListener = activity as menuListener?
        val bttn=view.findViewById<Button>(R.id.playGame)
        bttn.setOnClickListener {menuListener?.gameClicked() }
    }
}