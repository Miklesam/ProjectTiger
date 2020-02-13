package com.miklesam.dotamanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentLobby :Fragment(R.layout.fragment_lobby){

    interface LobbyListener {
        fun gameClicked()
        fun marketClicked()
        fun trainingClicked()
        fun teamClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lobbyListener = activity as LobbyListener
        val marketBttn=view.findViewById<Button>(R.id.marketBttn)
        val teamBttn=view.findViewById<Button>(R.id.teamBttn)
        val playBttn=view.findViewById<Button>(R.id.playBttn)
        val trainingBttn=view.findViewById<Button>(R.id.trainingBttn)
        marketBttn.setOnClickListener {lobbyListener.marketClicked() }
        teamBttn.setOnClickListener {lobbyListener.teamClicked() }
        playBttn.setOnClickListener {lobbyListener.gameClicked() }
        trainingBttn.setOnClickListener {lobbyListener.trainingClicked() }

    }
}
