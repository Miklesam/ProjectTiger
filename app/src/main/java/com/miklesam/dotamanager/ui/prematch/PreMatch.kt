package com.miklesam.dotamanager.ui.prematch

import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.ui.closedquali.ClosedRepository
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.plusDay
import kotlinx.android.synthetic.main.fragment_prematch.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class PreMatch : Fragment(R.layout.fragment_prematch) {

    interface afterCalculate {
        fun calculateTolobby()
        fun playGame()
    }

    private lateinit var preVM: PreMatchVM
    var didIWin = false
    var teams: List<TournamentTeam>? = null
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    var menuListener: afterCalculate? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuListener = activity as afterCalculate
        preVM = ViewModelProviders.of(this).get(PreMatchVM::class.java)
        val enemy = PrefsHelper.read(PrefsHelper.ENEMY_NAME, "")
        preVM.getState().observe(this, Observer {
            if (!it) {
                playMatch.visibility = VISIBLE
                calculateMatch.visibility = VISIBLE
                yourTeamScore.visibility = GONE
                enemyTeamScore.visibility = GONE
                matchResult.visibility = GONE
                nextAfterMatch.visibility = GONE
            } else {
                playMatch.visibility = GONE
                calculateMatch.visibility = GONE
                yourTeamScore.visibility = VISIBLE
                enemyTeamScore.visibility = VISIBLE
                matchResult.visibility = VISIBLE
                nextAfterMatch.visibility = VISIBLE
            }
        })
        scope.launch {
            ClosedRepository(activity!!.application).nukeClosed()
            PrefsHelper.write(PrefsHelper.CLOSED_QUALI_DAY, "1")
         }

        preVM.getTournamentTeams().observe(this, Observer {
            teams = it
        })

        enemy?.let {
            preVM.getTeamByName(it).observe(this, Observer {
                Glide.with(this)
                    .load(it.teamLogo)
                    .into(enemyImage)
                enemyTeamName.text = it.teamName
            })
        }

        preVM.getWinner().observe(this, Observer {
            didIWin = it
            if (it) {
                matchResult.text = "Radiant Victory"
                matchResult.setTextColor(ContextCompat.getColor(context!!, R.color.radiant_victory))
            } else {
                matchResult.text = "Dire Victory"
                matchResult.setTextColor(ContextCompat.getColor(context!!, R.color.dire_victory))
            }
        })
        preVM.getRadScore().observe(this, Observer {
            yourTeamScore.text = it.toString()
        })
        preVM.getDireScore().observe(this, Observer {
            enemyTeamScore.text = it.toString()
        })
        calculateMatch.setOnClickListener {
            preVM.setCalculate(true)
            val rndsRad = (0..45).random()
            val rndsDire = (0..45).random()
            preVM.setRadiant(rndsRad)
            preVM.setDire(rndsDire)
            if (rndsRad > rndsDire) {
                preVM.setWinner(true)
            } else {
                preVM.setWinner(false)
            }
        }
        winMatch.setOnClickListener { endMatchFlow(true) }
        loseMatch.setOnClickListener { endMatchFlow(false) }

        nextAfterMatch.setOnClickListener {
            endMatchFlow(didIWin)
        }

        playMatch.setOnClickListener { menuListener?.playGame() }


    }

    fun endMatchFlow(didIWin: Boolean) {

        val currebtClosedDay =
            PrefsHelper.read(PrefsHelper.CLOSED_QUALI_DAY, "1")?.toInt() ?: 1
        if (didIWin) {
            teams!![0].win = teams!![0].win + 1
            teams!![currebtClosedDay].lose = teams!![currebtClosedDay].lose + 1
        } else {
            teams!![0].lose = teams!![0].lose + 1
            teams!![currebtClosedDay].win = teams!![currebtClosedDay].win + 1
        }
        when (currebtClosedDay) {
            1 -> {
                generateMatch(teams!![2], teams!![3])
                generateMatch(teams!![7], teams!![8])
                generateMatch(teams!![5], teams!![6])
            }
            2 -> {
                generateMatch(teams!![1], teams!![4])
                generateMatch(teams!![6], teams!![9])
                generateMatch(teams!![5], teams!![7])
            }
            3 -> {
                generateMatch(teams!![2], teams!![4])
                generateMatch(teams!![1], teams!![3])
                generateMatch(teams!![7], teams!![9])
                generateMatch(teams!![5], teams!![8])
                generateMatch(teams!![6], teams!![8])
            }
            4 -> {
                generateMatch(teams!![1], teams!![2])
                generateMatch(teams!![3], teams!![4])
                generateMatch(teams!![6], teams!![7])
                generateMatch(teams!![5], teams!![9])
                generateMatch(teams!![8], teams!![9])
            }
        }



        scope.launch {
            preVM.updateTeams(teams!!)
        }
        plusDay()
        val closedDay = PrefsHelper.read(PrefsHelper.CLOSED_QUALI_DAY, "1")?.toInt()
        PrefsHelper.write(PrefsHelper.CLOSED_QUALI_DAY, (closedDay?.plus(1)).toString())
        menuListener?.calculateTolobby()
    }


    fun generateMatch(team1: TournamentTeam, team2: TournamentTeam) {
        val rndsRad = (0..45).random()
        val rndsDire = (0..45).random()
        if (rndsRad > rndsDire) {
            team1.win = team1.win + 1
            team2.lose = team2.lose + 1
        } else {
            team2.win = team2.win + 1
            team1.lose = team1.lose + 1
        }
    }
}