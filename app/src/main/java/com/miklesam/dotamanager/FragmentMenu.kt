package com.miklesam.dotamanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentMenu :Fragment(R.layout.fragment_menu){

    interface MenuListener {
        fun lobbyClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuListener = activity as MenuListener
        val bttn=view.findViewById<Button>(R.id.playGame)
        bttn.setOnClickListener {menuListener.lobbyClicked() }
    }
}