package com.miklesam.dotamanager.ui.game

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.dialogs.EndMatchDialog
import com.miklesam.dotamanager.dialogs.LaningDialog
import com.miklesam.dotamanager.myviews.GameSimulationView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.plusDay
import kotlinx.android.synthetic.main.fragment_game.*

class FragmentGame(myListener: backToLobby) : Fragment(R.layout.fragment_game),
    LaningDialog.NoticeDialogListener, EndMatchDialog.toLobbyInterface {
    var mListener: backToLobby = myListener
    val radiantImages =
        arrayOfNulls<ImageView>(5)
    val radiantHeroName =
        arrayOfNulls<TextView>(5)
    val radiantPlayerName =
        arrayOfNulls<TextView>(5)

    val direImages =
        arrayOfNulls<ImageView>(5)
    val direHeroName =
        arrayOfNulls<TextView>(5)
    var timer:CountDownTimer?=null

    interface backToLobby {
        fun backToLobbyCLicked()
    }

    var gameEnd = false
    override fun onDialogPositiveClick(position: Array<Int>) {
        gameGame?.CalcilateSpeed(
            arrayOf(
                position[0],
                position[1],
                position[2],
                position[3],
                position[4],
                3,
                5,
                5,
                4,
                3
            )
        )
        gameViewModel.calculateLineAssign(
            arrayOf(
                position[0],
                position[1],
                position[2],
                position[3],
                position[4],
                3,
                5,
                5,
                4,
                3
            )
        )
        timer = object : CountDownTimer(15000, 100) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                nextStage()
            }
        }
        timer?.start()


    }

    override fun onDialogDismissClick(position: Array<Int>) {
        Log.w("Dialog", "dismiss")
        gameGame?.CalcilateSpeed(
            arrayOf(
                position[0],
                position[1],
                position[2],
                position[3],
                position[4],
                3,
                5,
                5,
                4,
                3
            )
        )
        gameViewModel.calculateLineAssign(
            arrayOf(
                position[0],
                position[1],
                position[2],
                position[3],
                position[4],
                3,
                5,
                5,
                4,
                3
            )
        )
        timer = object : CountDownTimer(8000, 100) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                nextStage()
            }
        }
        timer?.start()

    }

    var gameGame: GameSimulationView? = null
    lateinit var soundPull: SoundPool
    var soundOne: Int = 0
    var soundTwo: Int = 0
    val TAG = "FragmentGame"
    var heroes: ArrayList<Int>? = null
    var direHeroes: ArrayList<Int>? = null
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameViewModel =
            ViewModelProviders.of(this).get(GameViewModel::class.java)
        if (arguments != null) {
            heroes = arguments!!.getIntegerArrayList(("radiant")!!)
            direHeroes = arguments!!.getIntegerArrayList(("dire")!!)
        }

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
        radiantImages[0] = firstRadiantPlayerHeroImage
        radiantImages[1] = secondRadiantPlayerHeroImage
        radiantImages[2] = thirdRadiantPlayerHeroImage
        radiantImages[3] = forthRadiantPlayerHeroImage
        radiantImages[4] = fifthRadiantPlayerHeroImage
        radiantHeroName[0] = heroRad1
        radiantHeroName[1] = heroRad2
        radiantHeroName[2] = heroRad3
        radiantHeroName[3] = heroRad4
        radiantHeroName[4] = heroRad5
        radiantPlayerName[0] = firstRadiantPlayerName
        radiantPlayerName[1] = secondRadiantPlayerName
        radiantPlayerName[2] = thirdRadiantPlayerName
        radiantPlayerName[3] = forthRadiantPlayerName
        radiantPlayerName[4] = fifthRadiantPlayerName


        direImages[0] = firstDirePlayerHeroImage
        direImages[1] = secondDirePlayerHeroImage
        direImages[2] = thirdDirePlayerHeroImage
        direImages[3] = forthDirePlayerHeroImage
        direImages[4] = fifthDirePlayerHeroImage

        direHeroName[0] = heroDire1
        direHeroName[1] = heroDire2
        direHeroName[2] = heroDire3
        direHeroName[3] = heroDire4
        direHeroName[4] = heroDire5
        val enemy=PrefsHelper.read(PrefsHelper.ENEMY_NAME,"")
        tagName.text = PrefsHelper.read(PrefsHelper.TEAM_NAME, "")
        tagName2.text=enemy

        enemy?.let {
            gameViewModel.getTeamByName(it).observe(this, Observer {
                Glide.with(this)
                    .load(it.teamLogo)
                    .into(tagImage2)
                firstDirePlayerName.text=it.playerPosition1
                secondDirePlayerName.text=it.playerPosition2
                thirdDirePlayerName.text=it.playerPosition3
                forthDirePlayerName.text=it.playerPosition4
                fifthDirePlayerName.text=it.playerPosition5
            })

        }




        for (i in 0 until 5) {
            radiantImages[i]?.setImageResource(
                Heroes.values().find { it.id == heroes?.get(i) ?: 0 }!!.icon
            )
            radiantHeroName[i]?.text =
                Heroes.values().find { it.id == heroes?.get(i) ?: 0 }!!.heroName

            direImages[i]?.setImageResource(
                Heroes.values().find { it.id == direHeroes?.get(i) ?: 0 }!!.icon
            )

            direHeroName[i]?.text =
                Heroes.values().find { it.id == direHeroes?.get(i) ?: 0 }!!.heroName
        }

        gameGame = view.findViewById(R.id.gameGame)
        heroes?.let { direHeroes?.let { it1 -> gameGame?.initHeroes(it, it1) } }
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
            radiantStat1.text = it[0]
            radiantStat2.text = it[1]
            radiantStat3.text = it[2]
            radiantStat4.text = it[3]
            radiantStat5.text = it[4]

            direStat1.text = it[5]
            direStat2.text = it[6]
            direStat3.text = it[7]
            direStat4.text = it[8]
            direStat5.text = it[9]

            radiantTotalScore.text = it[10]
            direTotalScore.text = it[11]

        })

        gameViewModel.getradiantTowers().observe(this, Observer {
            Log.w("Fragment Game", "Current TowerState= $it")
            gameGame?.setTowers(it)
            gameEnd = !it[9] || !it[19]
            if (!it[9]) {
                initiateEnd(2)
            } else {
                if (!it[19]) initiateEnd(1)
            }

        })

        gameViewModel?.getPlayer()?.observe(this, Observer {
            for (i in 0 until 5) {
                radiantPlayerName[i]?.text = it[i].nickname
            }
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.w(TAG, "onActivityCreated")

    }

    private fun CreateDeskDialog() {
        val dialog =
            LaningDialog(this, heroes)
        fragmentManager?.let { dialog.show(it, "CreateDeskDialog") }
    }


    override fun onStart() {
        super.onStart()
        Log.w(TAG, "onStart")
    }

    override fun onDestroyView() {
        gameGame = null
        timer=null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "onResume")
    }

    fun nextStage() {
        //gameViewModel.setStats(9)
        //soundPull.play(soundTwo, 1F, 1F, 0, 0, 1F)
        if (!gameEnd) {
            CreateDeskDialog()
        }
    }

    fun initiateEnd(side: Int) {
        Log.w(TAG, "Initiate End")
        gameGame?.initiateWin(side)
        CreateEndMatchDialogDialog(side)
    }

    private fun CreateEndMatchDialogDialog(side: Int) {
        val dialog = EndMatchDialog(this, side)
        fragmentManager?.let { dialog.show(it, "CreateEndMatchDialogDialog") }
    }

    override fun goToLobbyClick() {
        Log.w("FragmentGame", "EndGameClicked")
        plusDay()
        mListener.backToLobbyCLicked()
    }

}