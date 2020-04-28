package com.miklesam.dotamanager.simplefragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.WeatherView

class FragmentLobby : Fragment(R.layout.fragment_lobby) {

    private var weatherAnim: WeatherView?=null

    interface LobbyListener {
        fun gameClicked()
        fun marketClicked()
        fun trainingClicked()
        fun teamClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lobbyListener = activity as LobbyListener
        val marketBttn = view.findViewById<Button>(R.id.marketBttn)
        val teamBttn = view.findViewById<Button>(R.id.teamBttn)
        val playBttn = view.findViewById<Button>(R.id.playBttn)
        val trainingBttn = view.findViewById<Button>(R.id.trainingBttn)
        marketBttn.setOnClickListener { lobbyListener.marketClicked() }
        teamBttn.setOnClickListener { lobbyListener.teamClicked() }
        playBttn.setOnClickListener { lobbyListener.gameClicked() }
        trainingBttn.setOnClickListener { lobbyListener.trainingClicked() }
        weatherAnim = view.findViewById<WeatherView>(
            R.id.weatherAnim
        )
        weatherAnim?.start(view)
        Log.w("Pick"," Freagment Lobby ViewCreated")

    }

    override fun onResume() {
        weatherAnim?.resume()
        super.onResume()
        Log.w("Pick"," Freagment Lobby Resume")
    }

    override fun onPause() {
        weatherAnim?.pause()
        super.onPause()
        Log.w("Pick"," Freagment Lobby Pause")
    }

    override fun onDestroyView() {
        weatherAnim=null
        super.onDestroyView()
        Log.w("Pick"," Freagment Lobby DestroyView")
    }
}
