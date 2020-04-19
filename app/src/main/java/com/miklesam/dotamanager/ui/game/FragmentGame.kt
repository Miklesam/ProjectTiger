package com.miklesam.dotamanager.ui.game

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklesam.dotamanager.EndMatchDialog
import com.miklesam.dotamanager.LineningDialog
import com.miklesam.dotamanager.GameSimulationView
import com.miklesam.dotamanager.R
import kotlinx.android.synthetic.main.fragment_game.*

class FragmentGame(myListener: backToLobby) : Fragment(R.layout.fragment_game),
    LineningDialog.NoticeDialogListener, EndMatchDialog.toLobbyInterface {
    var mListener: backToLobby = myListener

    interface backToLobby{
        fun backToLobbyCLicked()
    }

    var gameEnd=false
    override fun onDialogPositiveClick(position: Array<Int>) {
        gameGame?.CalcilateSpeed(arrayOf(position[0],position[1],position[2],position[3],position[4],3,5,5,4,3))
        gameViewModel.calculateLineAssign(arrayOf(position[0],position[1],position[2],position[3],position[4],3,5,5,4,3))
        val timerAssignLine = object : CountDownTimer(15000, 100) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                nextStage()
            }
        }
        timerAssignLine.start()



    }

    override fun onDialogDismissClick(position: Array<Int>) {
        Log.w("Dialog", "dismiss")
        gameGame?.CalcilateSpeed(arrayOf(position[0],position[1],position[2],position[3],position[4],3,5,5,4,3))
        gameViewModel.calculateLineAssign(arrayOf(position[0],position[1],position[2],position[3],position[4],3,5,5,4,3))
        val timerAssignLine = object : CountDownTimer(8000, 100) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                nextStage()
            }
        }
        timerAssignLine.start()

    }

    var gameGame: GameSimulationView? = null
    lateinit var soundPull: SoundPool
    var soundOne: Int = 0
    var soundTwo: Int = 0
    val TAG = "FragmentGame"

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameViewModel =
            ViewModelProviders.of(this).get(GameViewModel::class.java)

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


        gameViewModel.getPlayersMatchStatistic().observe(this, Observer {
            Log.w("FragmentGame", it.toString())
            radiantStat1.text=it[0]
            radiantStat2.text=it[1]
            radiantStat3.text=it[2]
            radiantStat4.text=it[3]
            radiantStat5.text=it[4]

            direStat1.text=it[5]
            direStat2.text=it[6]
            direStat3.text=it[7]
            direStat4.text=it[8]
            direStat5.text=it[9]

            radiantTotalScore.text=it[10]
            direTotalScore.text=it[11]

        })

        gameViewModel.getradiantTowers().observe(this, Observer {
            Log.w("Fragment Game", "Current TowerState= $it")
            gameGame?.setTowers(it)
            gameEnd = !it[9]||!it[19]
            if(!it[9])
            {
                initiateEnd(2)
            }else{
                if(!it[19])initiateEnd(1)
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.w(TAG, "onActivityCreated")

    }

    private fun CreateDeskDialog() {
        val dialog = LineningDialog(this)
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
        //gameViewModel.setStats(9)
        //soundPull.play(soundTwo, 1F, 1F, 0, 0, 1F)
        if(!gameEnd){
            CreateDeskDialog()
        }
    }
    fun initiateEnd(side:Int){
        Log.w(TAG, "Initiate End")
        gameGame?.initiateWin(side)
        CreateEndMatchDialogDialog(side)
    }

    private fun CreateEndMatchDialogDialog(side: Int) {
        val dialog = EndMatchDialog(this,side)
        fragmentManager?.let { dialog.show(it, "CreateEndMatchDialogDialog") }
    }

    override fun goToLobbyClick() {
        Log.w("FragmentGame","EndGameClicked")
        mListener.backToLobbyCLicked()
    }

}