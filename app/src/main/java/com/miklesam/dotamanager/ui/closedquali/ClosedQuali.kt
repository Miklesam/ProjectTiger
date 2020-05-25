package com.miklesam.dotamanager.ui.closedquali

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.PlayoffTeam
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.room.teams.TeamsList
import com.miklesam.dotamanager.utils.*
import kotlinx.android.synthetic.main.closed_playoff_layout.*
import kotlinx.android.synthetic.main.closed_playoff_layout.view.*
import kotlinx.android.synthetic.main.closed_playoff_stage_layout.view.*
import kotlinx.android.synthetic.main.closed_playoff_team_layout.view.*
import kotlinx.android.synthetic.main.fragment_closed_quali.*
import kotlinx.android.synthetic.main.group_stage_layout.view.*
import kotlinx.android.synthetic.main.team_in_group_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ClosedQuali : Fragment(R.layout.fragment_closed_quali) {

    var teams: List<Team>? = null
    private var teamStats: List<TournamentTeam>? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val closedVM: ClosedQualiVM by viewModels()
    private var currentDay = 0
    private var myGroupPlace = 0
    private var groupAName =
        arrayOfNulls<TextView>(5)
    private var groupBName =
        arrayOfNulls<TextView>(5)
    private var groupAWin =
        arrayOfNulls<TextView>(5)
    private var groupALose =
        arrayOfNulls<TextView>(5)
    private var groupBWin =
        arrayOfNulls<TextView>(5)
    private var groupBLose =
        arrayOfNulls<TextView>(5)
    private var groupALogo =
        arrayOfNulls<ImageView>(5)
    private var groupBLogo =
        arrayOfNulls<ImageView>(5)


    private lateinit var playoffScoreList: List<MatchScore>
    private lateinit var sortedA: List<TournamentTeam>
    private lateinit var sortedB: List<TournamentTeam>

    interface ClosedQualListener {
        fun preMatchClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = activity as ClosedQualListener
        currentDay = PrefsHelper.read(PrefsHelper.CLOSED_QUALI_DAY, "1")?.toInt() ?: 1
        val day = "Day $currentDay"
        closedDay.text = day

        closedVM.getScore().observe(viewLifecycleOwner, Observer {
            playoffScoreList = it
            val listMatches = initPlayoffGrid()
            for (i in playoffScoreList.indices) {
                listMatches[i].first.score.text = playoffScoreList[i].topTeam.toString()
                listMatches[i].first.teamName.text = playoffScoreList[i].topTeamName
                setImageIn(playoffScoreList[i].topTeamLogo,listMatches[i].first.logo)

                listMatches[i].second.score.text = playoffScoreList[i].bottomTeam.toString()
                listMatches[i].second.teamName.text = playoffScoreList[i].bottomTeamName
                setImageIn(playoffScoreList[i].bottomTeamLogo,listMatches[i].second.logo)
            }
        })

        closedVM.getTeams().observe(viewLifecycleOwner, Observer { teams ->
            this.teams = teams
            if (!this.teams.isNullOrEmpty()) {
                closedVM.getTournamentTeams().observe(viewLifecycleOwner, Observer {
                    if (it.isEmpty()) {
                        val tournamentsArr = ArrayList<TournamentTeam>()
                        val yourTeam = PrefsHelper.read(PrefsHelper.TEAM_NAME, "")
                        tournamentsArr.add(
                            TournamentTeam(
                                yourTeam ?: "your Team",
                                TeamsList.CATEGORY_IMAGE_DIR + "yourteamlogo",
                                0,
                                0
                            )
                        )
                        for (team in this.teams!!) {
                            tournamentsArr.add(TournamentTeam(team.teamName, team.teamLogo, 0, 0))
                        }
                        scope.launch {
                            closedVM.initTournamentsTeams(tournamentsArr)
                        }
                        showCustomToast("Init succed", Toast.LENGTH_SHORT)
                    } else {
                        if (!it.isNullOrEmpty()) {
                            teamStats = it
                            val gropAteams = it.subList(0, 5)
                            val gropBteams = it.subList(5, 10)
                            sortedA = gropAteams.sortedByDescending { it.win }
                            sortedB = gropBteams.sortedByDescending { it.win }
                            initViews()
                            for (i in sortedA.indices) {
                                groupAName[i]?.text = sortedA[i].TeamName
                                groupAWin[i]?.text = sortedA[i].win.toString()
                                groupALose[i]?.text = sortedA[i].lose.toString()
                                groupALogo[i]?.let { it1 -> setImageIn(sortedA[i].logo, it1) }

                                groupBName[i]?.text = sortedB[i].TeamName
                                groupBWin[i]?.text = sortedB[i].win.toString()
                                groupBLose[i]?.text = sortedB[i].lose.toString()
                                groupBLogo[i]?.let { it1 -> setImageIn(sortedB[i].logo, it1) }

                            }

                        }

                    }
                })
            }
            initGroupColors()
        })


        playGame.setOnClickListener {
            if (currentDay >= 5) {
                val team =
                    sortedA.find { it.TeamName == PrefsHelper.read(PrefsHelper.TEAM_NAME, "") }
                myGroupPlace = sortedA.indexOf(team)
                if (myGroupPlace == 0 || myGroupPlace == 1) {
                    playoff.Visible()
                    playGame.Gone()

                    playoff.semifinal.playoffName.text = "Semi-finals"
                    playoff.uperfinal.playoffName.text = "Upper Bracket Final"
                    playoff.qualifoed.playoffName.text = "Qualified"
                    playoff.qualifoed2.playoffName.text = "Qualified"
                    playoff.play2.playoffName.text = "Lower Bracket R1"
                    playoff.lower_final.playoffName.text = "Lower Bracket Final"

                }

                when (myGroupPlace) {
                    0 -> {
                        if (playoffScoreList.isNullOrEmpty()) {
                            scope.launch {
                                closedVM.insertMatch(
                                    MatchScore(
                                        sortedA[0].TeamName,
                                        sortedB[1].TeamName,
                                        sortedA[0].logo,
                                        sortedB[1].logo,
                                        0,
                                        0,
                                        PlayOffState.SEMI_FINALS.id
                                    )
                                )
                                closedVM.insertMatch(
                                    MatchScore(
                                        sortedB[0].TeamName,
                                        sortedA[1].TeamName,
                                        sortedB[0].logo,
                                        sortedA[1].logo,
                                        0,
                                        0,
                                        PlayOffState.SEMI_FINALS.id
                                    )
                                )
                            }
                        }
                        /*
                        setImageIn(sortedA[0].logo,playoff.team1.teamImage)
                        playoff.team1.teamName.text = sortedA[0].TeamName
                        setImageIn(sortedB[1].logo,playoff.team2.teamImage)
                        playoff.team2.teamName.text = sortedB[1].TeamName

                        setImageIn(sortedA[1].logo,playoff.team3.teamImage)
                        playoff.team3.teamName.text = sortedA[1].TeamName
                        setImageIn(sortedB[0].logo,playoff.team4.teamImage)
                        playoff.team4.teamName.text = sortedB[0].TeamName
                         */

                    }
                    1 -> {
                        /*
                        setImageIn(sortedA[1].logo,playoff.team1.teamImage)
                        playoff.team1.teamName.text = sortedA[1].TeamName
                        setImageIn(sortedB[0].logo,playoff.team2.teamImage)
                        playoff.team2.teamName.text = sortedB[0].TeamName

                        setImageIn(sortedA[0].logo,playoff.team3.teamImage)
                        playoff.team3.teamName.text = sortedA[0].TeamName
                        setImageIn(sortedB[1].logo,playoff.team4.teamImage)
                        playoff.team4.teamName.text = sortedB[1].TeamName
                         */
                    }
                    else -> {
                        requireActivity().showDotaDialog(
                            "Вы понинули закрытую квалификацию",
                            "Возвращайтесь к тренировкам и улучшайте свою игру",
                            "вернуться к тренировкам"
                        )

                    }
                }
                if (currentDay >= 6) {
                    if (myGroupPlace == 0) {

                    } else {

                    }


                }
            } else {
                val teamEnemy = teamStats?.get(currentDay)?.TeamName
                teamEnemy?.let { PrefsHelper.write(PrefsHelper.ENEMY_NAME, it) }
                listener.preMatchClicked()
            }

        }

        nextPlayOff.setOnClickListener {
            if (currentDay == 5) {
                val teamEnemy = if (myGroupPlace == 0) {
                    sortedB[1].TeamName
                } else {
                    sortedB[0].TeamName
                }
                PrefsHelper.write(PrefsHelper.ENEMY_NAME, teamEnemy)
                listener.preMatchClicked()
            } else if (currentDay == 6) {
                listener.preMatchClicked()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        groupAName = emptyArray()
        groupBName = emptyArray()
        groupAWin = emptyArray()
        groupALose = emptyArray()
        groupBWin = emptyArray()
        groupBLose = emptyArray()
        groupALogo = emptyArray()
        groupBLogo = emptyArray()
    }

    private fun initPlayoffGrid(): List<Pair<PlayoffTeam, PlayoffTeam>> {
        return listOf(
            Pair(
                PlayoffTeam(
                    playoff.team1.teamImage,
                    playoff.team1.teamName,
                    playoff.team1.scoreTeam
                ),
                PlayoffTeam(
                    playoff.team2.teamImage,
                    playoff.team2.teamName,
                    playoff.team2.scoreTeam
                )
            ),
            Pair(
                PlayoffTeam(
                    playoff.team3.teamImage,
                    playoff.team3.teamName,
                    playoff.team3.scoreTeam
                ),
                PlayoffTeam(
                    playoff.team4.teamImage,
                    playoff.team4.teamName,
                    playoff.team4.scoreTeam
                )
            ),
            Pair(
                PlayoffTeam(
                    playoff.team5.teamImage,
                    playoff.team5.teamName,
                    playoff.team5.scoreTeam
                ),
                PlayoffTeam(
                    playoff.team6.teamImage,
                    playoff.team6.teamName,
                    playoff.team6.scoreTeam
                )
            ),
            Pair(
                PlayoffTeam(
                    playoff.team7.teamImage,
                    playoff.team7.teamName,
                    playoff.team7.scoreTeam
                ),
                PlayoffTeam(
                    playoff.team8.teamImage,
                    playoff.team8.teamName,
                    playoff.team8.scoreTeam
                )
            ),
            Pair(
                PlayoffTeam(
                    playoff.team9.teamImage,
                    playoff.team9.teamName,
                    playoff.team9.scoreTeam
                ),
                PlayoffTeam(
                    playoff.team10.teamImage,
                    playoff.team10.teamName,
                    playoff.team10.scoreTeam
                )
            )
        )
    }

    private fun setImageIn(image: String, imageView: ImageView) {
        Glide.with(this)
            .load(image)
            .into(imageView)
    }

    private fun initViews() {
        groupAName = arrayOf(
            groupLayoutA.group1.TeamName,
            groupLayoutA.group2.TeamName,
            groupLayoutA.group3.TeamName,
            groupLayoutA.group4.TeamName,
            groupLayoutA.group5.TeamName
        )
        groupAWin = arrayOf(
            groupLayoutA.group1.ScoreWin,
            groupLayoutA.group2.ScoreWin,
            groupLayoutA.group3.ScoreWin,
            groupLayoutA.group4.ScoreWin,
            groupLayoutA.group5.ScoreWin
        )

        groupALose = arrayOf(
            groupLayoutA.group1.ScoreLose,
            groupLayoutA.group2.ScoreLose,
            groupLayoutA.group3.ScoreLose,
            groupLayoutA.group4.ScoreLose,
            groupLayoutA.group5.ScoreLose
        )

        groupALogo = arrayOf(
            groupLayoutA.group1.Teamlogo,
            groupLayoutA.group2.Teamlogo,
            groupLayoutA.group3.Teamlogo,
            groupLayoutA.group4.Teamlogo,
            groupLayoutA.group5.Teamlogo
        )


        groupBName = arrayOf(
            groupLayoutB.group1.TeamName,
            groupLayoutB.group2.TeamName,
            groupLayoutB.group3.TeamName,
            groupLayoutB.group4.TeamName,
            groupLayoutB.group5.TeamName
        )
        groupBWin = arrayOf(
            groupLayoutB.group1.ScoreWin,
            groupLayoutB.group2.ScoreWin,
            groupLayoutB.group3.ScoreWin,
            groupLayoutB.group4.ScoreWin,
            groupLayoutB.group5.ScoreWin
        )

        groupBLose = arrayOf(
            groupLayoutB.group1.ScoreLose,
            groupLayoutB.group2.ScoreLose,
            groupLayoutB.group3.ScoreLose,
            groupLayoutB.group4.ScoreLose,
            groupLayoutB.group5.ScoreLose
        )

        groupBLogo = arrayOf(
            groupLayoutB.group1.Teamlogo,
            groupLayoutB.group2.Teamlogo,
            groupLayoutB.group3.Teamlogo,
            groupLayoutB.group4.Teamlogo,
            groupLayoutB.group5.Teamlogo
        )


    }

    private fun initGroupColors() {
        groupLayoutA.group1.setBackgroundColor(resources.getColor(R.color.high_green_group))
        groupLayoutA.group2.setBackgroundColor(resources.getColor(R.color.high_green_group))
        groupLayoutA.group3.setBackgroundColor(resources.getColor(R.color.orange_group))
        groupLayoutA.group4.setBackgroundColor(resources.getColor(R.color.red_group))
        groupLayoutA.group5.setBackgroundColor(resources.getColor(R.color.red_group))

        groupLayoutA.group1.place.text = "1."
        groupLayoutA.group2.place.text = "2."
        groupLayoutA.group3.place.text = "3."
        groupLayoutA.group4.place.text = "4."
        groupLayoutA.group5.place.text = "5."

        groupLayoutB.group1.setBackgroundColor(resources.getColor(R.color.high_green_group))
        groupLayoutB.group2.setBackgroundColor(resources.getColor(R.color.high_green_group))
        groupLayoutB.group3.setBackgroundColor(resources.getColor(R.color.orange_group))
        groupLayoutB.group4.setBackgroundColor(resources.getColor(R.color.red_group))
        groupLayoutB.group5.setBackgroundColor(resources.getColor(R.color.red_group))


        groupLayoutB.group1.place.text = "1."
        groupLayoutB.group2.place.text = "2."
        groupLayoutB.group3.place.text = "3."
        groupLayoutB.group4.place.text = "4."
        groupLayoutB.group5.place.text = "5."
    }

}