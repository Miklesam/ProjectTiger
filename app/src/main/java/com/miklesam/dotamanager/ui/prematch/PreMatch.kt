package com.miklesam.dotamanager.ui.prematch

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.ui.closedquali.ClosedRepository
import com.miklesam.dotamanager.utils.PlayOffState
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.plusDay
import com.miklesam.dotamanager.utils.showCustomToast
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

    private val preVM: PreMatchVM by viewModels()
    private var didIWin = false
    var teams: List<TournamentTeam>? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var menuListener: afterCalculate? = null
    private lateinit var scoreList: List<MatchScore>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuListener = activity as afterCalculate
        val enemy = PrefsHelper.read(PrefsHelper.ENEMY_NAME, "")
        preVM.getState().observe(viewLifecycleOwner, Observer {
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

        enemyImage.setOnClickListener {
            scope.launch {
                ClosedRepository(requireActivity().application).nukeClosed()
                PreMatchRepo(requireActivity().application).nukeScore()
                PrefsHelper.write(PrefsHelper.CLOSED_QUALI_DAY, "1")
            }
            showCustomToast("Nuked", Toast.LENGTH_SHORT)
        }


        preVM.getTournamentTeams().observe(viewLifecycleOwner, Observer {
            teams = it
        })

        preVM.getCLosedPlayoffScore().observe(viewLifecycleOwner, Observer {
            scoreList = it
        })

        enemy?.let {
            preVM.getTeamByName(it).observe(viewLifecycleOwner, Observer {
                Glide.with(this)
                    .load(it.teamLogo)
                    .into(enemyImage)
                enemyTeamName.text = it.teamName
            })
        }

        preVM.getWinner().observe(viewLifecycleOwner, Observer {
            didIWin = it
            if (it) {
                matchResult.text = "Radiant Victory"
                matchResult.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.radiant_victory
                    )
                )
            } else {
                matchResult.text = "Dire Victory"
                matchResult.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dire_victory
                    )
                )
            }
        })
        preVM.getRadScore().observe(viewLifecycleOwner, Observer {
            yourTeamScore.text = it.toString()
        })
        preVM.getDireScore().observe(viewLifecycleOwner, Observer {
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

    private fun endMatchFlow(didIWin: Boolean) {

        val currentClosedDay =
            PrefsHelper.read(PrefsHelper.CLOSED_QUALI_DAY, "1")?.toInt() ?: 1
        if (currentClosedDay <= 4) {

            if (didIWin) {
                teams!![0].win = teams!![0].win + 1
                teams!![currentClosedDay].lose = teams!![currentClosedDay].lose + 1
            } else {
                teams!![0].lose = teams!![0].lose + 1
                teams!![currentClosedDay].win = teams!![currentClosedDay].win + 1
            }
            when (currentClosedDay) {
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
        } else {
            scope.launch {
                val yourTeamName = PrefsHelper.read(PrefsHelper.TEAM_NAME, "") ?: ""
                val yourEnemyName = PrefsHelper.read(PrefsHelper.ENEMY_NAME, "") ?: ""
                val myMatch = when (currentClosedDay) {
                    5 -> {
                        scoreList.find { (it.topTeamName == yourTeamName) && (it.bottomTeamName == yourEnemyName) }
                    }
                    6 -> {
                        scoreList.find {
                            (it.topTeamName == yourTeamName) && (it.bottomTeamName == yourEnemyName) &&
                                    (it.playoffStage == PlayOffState.UPPER_BRACKET_FINAL.id || it.playoffStage == PlayOffState.LOWER_BRACKER_R1.id)
                        }
                    }
                    7 -> {
                        scoreList.find {
                            (
                                    (it.topTeamName == yourTeamName) && (it.bottomTeamName == yourEnemyName)
                                            || (it.bottomTeamName == yourTeamName) && (it.topTeamName == yourEnemyName)
                                    )
                                    && (it.playoffStage == PlayOffState.LOWER_BRACKET_FINAL.id)
                        }
                    }
                    else -> {
                        null
                    }
                }

                myMatch?.let { match ->
                    if (didIWin) {
                        if (currentClosedDay == 7) {
                            val amITop = scoreList[4].topTeamName == yourTeamName
                            if (amITop) {
                                match.topTeam++
                            } else {
                                match.bottomTeam++
                            }
                        } else {
                            match.topTeam++
                        }
                    } else {
                        if (currentClosedDay == 7) {
                            val amITop = scoreList[4].topTeamName == yourTeamName
                            if (amITop) {
                                match.bottomTeam++
                            } else {
                                match.topTeam++
                            }
                        } else {
                            match.bottomTeam++
                        }

                    }

                    if (match.topTeam == 2 || match.bottomTeam == 2) {
                        val otherMatch =
                            scoreList.find { (it.topTeam == 0) && (it.bottomTeam == 0) }
                        otherMatch?.let {
                            generateOtherScore(it)
                        }
                        plusDay()
                        val closedDay = PrefsHelper.read(PrefsHelper.CLOSED_QUALI_DAY, "1")?.toInt()
                        PrefsHelper.write(
                            PrefsHelper.CLOSED_QUALI_DAY,
                            (closedDay?.plus(1)).toString()
                        )
                    }

                }


                preVM.updateScore(scoreList)
            }
            menuListener?.calculateTolobby()

        }

    }

    private fun generateOtherScore(matchScore: MatchScore): MatchScore {
        while (matchScore.topTeam != 2 && matchScore.bottomTeam != 2) {
            val rndsRad = (0..45).random()
            val rndsDire = (0..45).random()
            if (rndsRad > rndsDire) {
                matchScore.topTeam++
            } else {
                matchScore.bottomTeam++
            }
        }
        return matchScore
    }


    private fun generateMatch(team1: TournamentTeam, team2: TournamentTeam) {
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