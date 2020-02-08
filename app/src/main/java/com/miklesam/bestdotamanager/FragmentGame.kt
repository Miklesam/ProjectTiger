package com.miklesam.bestdotamanager

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*

class FragmentGame :Fragment(R.layout.fragment_game) {
    var gameGame: GameSimulationView? =null
    var player : MediaPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameGame=view.findViewById<GameSimulationView>(R.id.gameGame)
        val tagName=view.findViewById<TextView>(R.id.tagName)
        val tagName2=view.findViewById<TextView>(R.id.tagName2)
        gameGame?.Start()

        tagName.setOnClickListener {
            gameGame?.CalcilateSpeed(0F,300F)
            play()
        }
        tagName2.setOnClickListener {
            gameGame?.CalcilateSpeed(300F,0F)
        }


       /*
        val cd = object : CountDownTimer(2000, 100) {

            override fun onTick(l: Long) {
                Log.w("GameView", "Click")
            }

            override fun onFinish() {
                Log.w("GameView", "Finish")
                gameGame.pause()
            }
        }.start()
*/
    }

    fun play(){
        if(player==null){
            player=MediaPlayer.create(context,R.raw.hello_vp_navi)
        }
        player?.start()
    }

    fun stopPlayer(){
        if(player!=null){
            player!!.release()
            player=null

        }
    }

}