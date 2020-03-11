package com.miklesam.dotamanager.ui

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.CreateDialog
import com.miklesam.dotamanager.GameSimulationView
import com.miklesam.dotamanager.R
import kotlin.random.Random

class FragmentGame : Fragment(R.layout.fragment_game),
    CreateDialog.NoticeDialogListener {
    override fun onDialogPositiveClick(position: Array<Int>) {
        gameGame?.CalcilateSpeed(arrayOf(position[0],position[1],position[2],position[3],position[4],3,5,5,4,3))
        val timerAssignLine = object : CountDownTimer(25000, 100) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                //CreateDeskDialog()
                nextStage()
            }
        }
        timerAssignLine.start()



    }

    var gameGame: GameSimulationView? = null
    var radiantTotalScore: TextView? = null
    var direTotalScore: TextView? = null


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
        radiantTotalScore=view.findViewById(R.id.radiantTotalScore)
        direTotalScore=view.findViewById(R.id.direTotalScore)
        gameGame?.Start()

        val timerAssignLine = object : CountDownTimer(2000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                gameGame?.setBasePosition()
            }
            override fun onFinish() {
                soundPull.play(soundOne, 1F, 1F, 0, 0, 1F)
                val timer = object : CountDownTimer(1500, 100) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        soundPull.play(soundTwo, 1F, 1F, 0, 0, 1F)
                        CreateDeskDialog()
                    }
                }
                timer.start()
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

    fun nextStage(){
        direTotalScore?.text= Random.nextInt(0, 10).toString()
        radiantTotalScore?.text=Random.nextInt(0, 10).toString()
        //soundPull.play(soundTwo, 1F, 1F, 0, 0, 1F)
    }

}