package com.miklesam.dotamanager.multipleer

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.miklesam.dotamanager.GameSimulationView
import com.miklesam.dotamanager.LineningDialog
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.multipleer.client.ClientViewModel
import com.miklesam.dotamanager.multipleer.host.HostViewModel
import kotlinx.android.synthetic.main.fragment_game.*

class MultiGame(isHost: Boolean) : Fragment(R.layout.fragment_game),
    LineningDialog.NoticeDialogListener {
    private lateinit var myViewModel: ViewModel
    var host = isHost
    var multiGame: GameSimulationView? = null
    var radiant = ArrayList<Int>()
    var dire = ArrayList<Int>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagName.text = ""
        tagName2.text = ""
        firstRadiantPlayerName.text = ""
        firstDirePlayerName.text = ""

        secondRadiantPlayerName.text = ""
        secondDirePlayerName.text = ""

        thirdRadiantPlayerName.text = ""
        thirdDirePlayerName.text = ""

        forthRadiantPlayerName.text = ""
        forthDirePlayerName.text = ""

        fifthRadiantPlayerName.text = ""
        fifthDirePlayerName.text = ""
        tagImage2.setImageResource(android.R.color.transparent)

        if (host) {
            myViewModel = ViewModelProviders.of(requireActivity()).get(HostViewModel::class.java)
            //(myViewModel as HostViewModel).startPick()
            (myViewModel as HostViewModel).getTicTac().observe(this, Observer { picksArray ->
                showImages(picksArray)
            })
        } else {
            myViewModel = ViewModelProviders.of(requireActivity()).get(ClientViewModel::class.java)
            (myViewModel as ClientViewModel).getTicTac().observe(this, Observer { picksArray ->
                showImages(picksArray)
            })
        }

        multiGame = view.findViewById(R.id.gameGame)
        multiGame?.Start()
        val timerAssignLine = object : CountDownTimer(2000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                multiGame?.setBasePosition()
            }

            override fun onFinish() {
                //soundPull.play(soundOne, 1F, 1F, 0, 0, 1F)
                val timer = object : CountDownTimer(1500, 100) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        //soundPull.play(soundTwo, 1F, 1F, 0, 0, 1F)
                        CreateDeskDialog()
                    }
                }
                timer.start()
            }
        }
        timerAssignLine.start()

        if (host) {
            (myViewModel as HostViewModel).getMoveLinning().observe(this, Observer {
                if (it[0] != 7) {
                    multiGame?.CalcilateSpeed(it)
                }
            })

            (myViewModel as HostViewModel).getPlayersMatchStatistic().observe(this, Observer {
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

            (myViewModel as HostViewModel).getradiantTowers().observe(this, Observer {
                Log.w("Fragment Game", "Current TowerState= $it")
                gameGame?.setTowers(it)
                /*
                gameEnd = !it[9] || !it[19]
                if (!it[9]) {
                    initiateEnd(2)
                } else {
                    if (!it[19]) initiateEnd(1)
                }

                 */

            })


        } else {
            (myViewModel as ClientViewModel).getMoveLinning().observe(this, Observer {
                if (it[0] != 7) {
                    multiGame?.CalcilateSpeed(it)
                }
            })

            (myViewModel as ClientViewModel).getPlayersMatchStatistic().observe(this, Observer {
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
        }


    }

    private fun showImages(picksArray: Array<Int>) {
        firstRadiantPlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[8] }!!.icon
        )
        secondRadiantPlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[11] }!!.icon
        )
        thirdRadiantPlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[15] }!!.icon
        )
        forthRadiantPlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[17] }!!.icon
        )
        fifthRadiantPlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[20] }!!.icon
        )
        heroRad1.text = Heroes.values().find { it.id == picksArray[8] }!!.heroName
        heroRad2.text = Heroes.values().find { it.id == picksArray[11] }!!.heroName
        heroRad3.text = Heroes.values().find { it.id == picksArray[15] }!!.heroName
        heroRad4.text = Heroes.values().find { it.id == picksArray[17] }!!.heroName
        heroRad5.text = Heroes.values().find { it.id == picksArray[20] }!!.heroName

        firstDirePlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[9] }!!.icon
        )
        secondDirePlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[10] }!!.icon
        )
        thirdDirePlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[14] }!!.icon
        )
        forthDirePlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[16] }!!.icon
        )
        fifthDirePlayerHeroImage.setImageResource(
            Heroes.values().find { it.id == picksArray[21] }!!.icon
        )

        heroDire1.text = Heroes.values().find { it.id == picksArray[9] }!!.heroName
        heroDire2.text = Heroes.values().find { it.id == picksArray[10] }!!.heroName
        heroDire3.text = Heroes.values().find { it.id == picksArray[14] }!!.heroName
        heroDire4.text = Heroes.values().find { it.id == picksArray[16] }!!.heroName
        heroDire5.text = Heroes.values().find { it.id == picksArray[21] }!!.heroName

        radiant = arrayListOf(
            picksArray[8],
            picksArray[11],
            picksArray[15],
            picksArray[17],
            picksArray[20]
        )
        dire = arrayListOf(
            picksArray[9],
            picksArray[10],
            picksArray[14],
            picksArray[16],
            picksArray[21]
        )
        multiGame?.initHeroes(radiant, dire)


    }

    private fun CreateDeskDialog() {
        if (host) {
            val dialog = LineningDialog(this, radiant)
            fragmentManager?.let { dialog.show(it, "CreateDeskDialog") }
        } else {
            val dialog = LineningDialog(this, dire)
            fragmentManager?.let { dialog.show(it, "CreateDeskDialog") }
        }

    }

    override fun onDialogPositiveClick(position: Array<Int>) {
        if (host) {
            (myViewModel as HostViewModel).setRadiantLaining(position)
        } else {
            (myViewModel as ClientViewModel).setDireLaining(position)
        }
        /*
        gameGame?.CalcilateSpeed(
            arrayOf(
                0,
                0,
                1,
                2,
                2,
                3,
                5,
                5,
                4,
                3
            )
        )
         */

    }

    override fun onDialogDismissClick(position: Array<Int>) {
        /*
        gameGame?.CalcilateSpeed(
            arrayOf(
                0,
                0,
                1,
                2,
                2,
                3,
                5,
                5,
                4,
                3
            )
        )

         */
    }
}