package com.miklesam.dotamanager

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*
import java.util.ArrayList

class FragmentGame : Fragment(R.layout.fragment_game), CreateDialog.NoticeDialogListener {
    override fun onDialogPositiveClick(dialog: String) {
        Log.w(TAG, "onDialogPositiveClick $dialog")
        gameGame?.CalcilateSpeed(dialog.toInt())
        val timerAssignLine = object : CountDownTimer(3000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                //gameGame?.setBasePosition()
            }
            override fun onFinish() {
                CreateDeskDialog()
            }
        }
        timerAssignLine.start()



    }

    var gameGame: GameSimulationView? = null
    lateinit var soundPull: SoundPool
    var soundOne: Int = 0
    var soundTwo: Int = 0
    val TAG = "FragmentGame"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(TAG, "onCreate")
        val audioAtributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPull = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAtributes)
            .build()
        soundOne = soundPull.load(context, R.raw.hello_casper, 1)
        soundTwo = soundPull.load(context, R.raw.hello_v1lat, 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w(TAG, "onViewCreated")
        gameGame = view.findViewById<GameSimulationView>(R.id.gameGame)
        val tagName = view.findViewById<TextView>(R.id.tagName)
        val tagName2 = view.findViewById<TextView>(R.id.tagName2)
        gameGame?.Start()
        tagName.setOnClickListener {
            soundPull.play(soundOne, 1F, 1F, 0, 0, 1F)
            val timer = object : CountDownTimer(1500, 100) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    soundPull.play(soundTwo, 1F, 1F, 0, 0, 1F)
                }
            }
            timer.start()
        }


        val timerAssignLine = object : CountDownTimer(2000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                gameGame?.setBasePosition()
            }
            override fun onFinish() {
                CreateDeskDialog()
            }
        }
        timerAssignLine.start()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.w(TAG, "onActivityCreated")

    }

    private fun CreateDeskDialog() {
        val dialog = CreateDialog(this)
        fragmentManager?.let { dialog.show(it, "CreateDeskDialog") }
    }


    override fun onStart() {
        super.onStart()
        Log.w(TAG, "onStart")
    }

    override fun onDestroyView() {
        gameGame = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "onResume")
    }
}