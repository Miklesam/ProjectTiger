package com.miklesam.dotamanager.multipleer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import kotlinx.android.synthetic.main.fragment_game.*

class MultiGame(host: Boolean) : Fragment(R.layout.fragment_game){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagName.text=""
        tagName2.text=""
        firstRadiantPlayerName.text=""
        firstDirePlayerName.text=""

        secondRadiantPlayerName.text=""
        secondDirePlayerName.text=""

        thirdRadiantPlayerName.text=""
        thirdDirePlayerName.text=""

        forthRadiantPlayerName.text=""
        forthDirePlayerName.text=""

        fifthRadiantPlayerName.text=""
        fifthDirePlayerName.text=""
        tagImage2.setImageResource(android.R.color.transparent)


    }
}