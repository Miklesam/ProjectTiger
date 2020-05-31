package com.miklesam.dotamanager.ui.minortournament

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
import com.miklesam.dotamanager.scope
import com.miklesam.dotamanager.ui.closedquali.ClosedQuali
import com.miklesam.dotamanager.utils.*
import kotlinx.android.synthetic.main.closed_playoff_layout.*
import kotlinx.android.synthetic.main.closed_playoff_layout.view.*
import kotlinx.android.synthetic.main.closed_playoff_layout.view.lower_final
import kotlinx.android.synthetic.main.closed_playoff_layout.view.nextPlayOff
import kotlinx.android.synthetic.main.closed_playoff_layout.view.play2
import kotlinx.android.synthetic.main.closed_playoff_layout.view.qualifoed
import kotlinx.android.synthetic.main.closed_playoff_layout.view.semifinal
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team1
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team10
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team11
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team12
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team2
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team3
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team4
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team5
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team6
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team7
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team8
import kotlinx.android.synthetic.main.closed_playoff_layout.view.team9
import kotlinx.android.synthetic.main.closed_playoff_layout.view.uperfinal
import kotlinx.android.synthetic.main.closed_playoff_stage_layout.view.*
import kotlinx.android.synthetic.main.closed_playoff_team_layout.view.*
import kotlinx.android.synthetic.main.fragment_closed_quali.*
import kotlinx.android.synthetic.main.fragment_minor.*
import kotlinx.android.synthetic.main.fragment_minor.groupLayoutA
import kotlinx.android.synthetic.main.fragment_minor.groupLayoutB
import kotlinx.android.synthetic.main.fragment_minor.playoff
import kotlinx.android.synthetic.main.minor_group_stage_layout.view.*
import kotlinx.android.synthetic.main.minor_playoff_layout.view.*
import kotlinx.android.synthetic.main.team_in_group_layout.view.*
import kotlinx.coroutines.launch

class Minor : Fragment(R.layout.fragment_minor) {
    private val minorVM: MinorVM by viewModels()
    var teams: List<Team>? = null
    private lateinit var playoffScoreList: List<MatchScore>
    private var teamStats: List<TournamentTeam>? = null
    private var groupAName =
        arrayOfNulls<TextView>(4)
    private var groupBName =
        arrayOfNulls<TextView>(4)
    private var groupAWin =
        arrayOfNulls<TextView>(4)
    private var groupALose =
        arrayOfNulls<TextView>(4)
    private var groupBWin =
        arrayOfNulls<TextView>(4)
    private var groupBLose =
        arrayOfNulls<TextView>(4)
    private var groupALogo =
        arrayOfNulls<ImageView>(4)
    private var groupBLogo =
        arrayOfNulls<ImageView>(4)

    private lateinit var sortedA: List<TournamentTeam>
    private lateinit var sortedB: List<TournamentTeam>
    private var currentDay = 0
    private var myGroupPlace = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = activity as ClosedQuali.ClosedQualListener
        initViews()
        initGroupColors()
        currentDay = PrefsHelper.read(PrefsHelper.MINOR_DAY, "1")?.toInt() ?: 1
        val day = "Day $currentDay"
        closedDayMinorTournament.text = day

        minorVM.getTeams().observe(viewLifecycleOwner, Observer {
            teams = it
            minorVM.getTournamentTeams().observe(viewLifecycleOwner, Observer {
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
                        minorVM.initTournamentsTeams(tournamentsArr)
                    }
                    showCustomToast("Init succed", Toast.LENGTH_SHORT)
                } else {
                    if (!it.isNullOrEmpty()) {
                        teamStats = it
                        val gropAteams = it.subList(0, 4)
                        val gropBteams = it.subList(4, 8)
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

            minorVM.getScore().observe(viewLifecycleOwner, Observer {
                playoffScoreList = it
                val listMatches = initPlayoffGrid()
                for (i in playoffScoreList.indices) {
                    listMatches[i].first.score.text = playoffScoreList[i].topTeam.toString()
                    listMatches[i].first.teamName.text = playoffScoreList[i].topTeamName
                    setImageIn(playoffScoreList[i].topTeamLogo, listMatches[i].first.logo)

                    listMatches[i].second.score.text = playoffScoreList[i].bottomTeam.toString()
                    listMatches[i].second.teamName.text = playoffScoreList[i].bottomTeamName
                    setImageIn(playoffScoreList[i].bottomTeamLogo, listMatches[i].second.logo)
                }
            })


        })
        playGameMinor.setOnClickListener {
            if (currentDay >= 4) {
                val team =
                    sortedA.find { it.TeamName == PrefsHelper.read(PrefsHelper.TEAM_NAME, "") }
                myGroupPlace = sortedA.indexOf(team)
                if (myGroupPlace == 0 || myGroupPlace == 1) {
                    playoff.Visible()
                    playGameMinor.Gone()

                    playoff.semifinal.playoffName.text = "Semi-finals"
                    playoff.uperfinal.playoffName.text = "Upper Bracket Final"
                    playoff.qualifoed.playoffName.text = "Grand Final"
                    playoff.champion.playoffName.text = "Champion"
                    playoff.play2.playoffName.text = "Lower Bracket R1"
                    playoff.lower_final.playoffName.text = "Lower Bracket Final"

                }
                when (myGroupPlace) {
                    0 -> {
                        if (playoffScoreList.isNullOrEmpty()) {
                            scope.launch {
                                minorVM.insertMatch(
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
                                minorVM.insertMatch(
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
                    }
                    1 -> {
                        if (playoffScoreList.isNullOrEmpty()) {
                            scope.launch {
                                minorVM.insertMatch(
                                    MatchScore(
                                        sortedA[1].TeamName,
                                        sortedB[0].TeamName,
                                        sortedA[1].logo,
                                        sortedB[0].logo,
                                        0,
                                        0,
                                        PlayOffState.SEMI_FINALS.id
                                    )
                                )
                                minorVM.insertMatch(
                                    MatchScore(
                                        sortedB[1].TeamName,
                                        sortedA[0].TeamName,
                                        sortedB[1].logo,
                                        sortedA[0].logo,
                                        0,
                                        0,
                                        PlayOffState.SEMI_FINALS.id
                                    )
                                )
                            }
                        }
                    }
                    else -> {
                        loseClosedQuali()
                    }
                }
                if (currentDay >= 5) {
                    if (playoffScoreList.size < 3) {
                        var match1WinnerName = ""
                        var match1WinnerLogo = ""
                        var match1LoserName = ""
                        var match1LoserLogo = ""
                        if (playoffScoreList[0].topTeam == 2) {
                            match1WinnerName = playoffScoreList[0].topTeamName
                            match1WinnerLogo = playoffScoreList[0].topTeamLogo
                            match1LoserName = playoffScoreList[0].bottomTeamName
                            match1LoserLogo = playoffScoreList[0].bottomTeamLogo
                        } else {
                            match1WinnerName = playoffScoreList[0].bottomTeamName
                            match1WinnerLogo = playoffScoreList[0].bottomTeamLogo
                            match1LoserName = playoffScoreList[0].topTeamName
                            match1LoserLogo = playoffScoreList[0].topTeamLogo
                        }

                        var match2WinnerName = ""
                        var match2WinnerLogo = ""
                        var match2LoserName = ""
                        var match2LoserLogo = ""
                        if (playoffScoreList[1].topTeam == 2) {
                            match2WinnerName = playoffScoreList[1].topTeamName
                            match2WinnerLogo = playoffScoreList[1].topTeamLogo
                            match2LoserName = playoffScoreList[1].bottomTeamName
                            match2LoserLogo = playoffScoreList[1].bottomTeamLogo
                        } else {
                            match2WinnerName = playoffScoreList[1].bottomTeamName
                            match2WinnerLogo = playoffScoreList[1].bottomTeamLogo
                            match2LoserName = playoffScoreList[1].topTeamName
                            match2LoserLogo = playoffScoreList[1].topTeamLogo
                        }
                        scope.launch {
                            minorVM.insertMatch(
                                MatchScore(
                                    match1LoserName,
                                    match2LoserName,
                                    match1LoserLogo,
                                    match2LoserLogo,
                                    0,
                                    0,
                                    PlayOffState.LOWER_BRACKER_R1.id
                                )
                            )
                            minorVM.insertMatch(
                                MatchScore(
                                    match1WinnerName,
                                    match2WinnerName,
                                    match1WinnerLogo,
                                    match2WinnerLogo,
                                    0,
                                    0,
                                    PlayOffState.UPPER_BRACKET_FINAL.id
                                )
                            )
                        }

                    }
                }
                if (currentDay >= 6) {
                    var match2WinnerName = ""
                    var match2WinnerLogo = ""
                    var match1WinnerName = ""
                    var match1WinnerLogo = ""
                    if (playoffScoreList[2].topTeam == 2) {
                        match1WinnerName = playoffScoreList[2].topTeamName
                        match1WinnerLogo = playoffScoreList[2].topTeamLogo
                    } else {
                        match1WinnerName = playoffScoreList[2].bottomTeamName
                        match1WinnerLogo = playoffScoreList[2].bottomTeamLogo
                    }
                    var match2LoserName = ""
                    var match2LoserLogo = ""
                    if (playoffScoreList[3].topTeam == 2) {
                        match2WinnerName = playoffScoreList[3].topTeamName
                        match2WinnerLogo = playoffScoreList[3].topTeamLogo
                        match2LoserName = playoffScoreList[3].bottomTeamName
                        match2LoserLogo = playoffScoreList[3].bottomTeamLogo
                    } else {
                        match2WinnerName = playoffScoreList[3].bottomTeamName
                        match2WinnerLogo = playoffScoreList[3].bottomTeamLogo
                        match2LoserName = playoffScoreList[3].topTeamName
                        match2LoserLogo = playoffScoreList[3].topTeamLogo
                    }
                    if (playoffScoreList.size < 5) {
                        scope.launch {
                            minorVM.insertMatch(
                                MatchScore(
                                    match1WinnerName,
                                    match2LoserName,
                                    match1WinnerLogo,
                                    match2LoserLogo,
                                    0,
                                    0,
                                    PlayOffState.LOWER_BRACKET_FINAL.id
                                )
                            )
                        }
                    }
                    playoff.team11.teamName.text = match2WinnerName
                    setImageIn(match2WinnerLogo, playoff.team11.teamImage)
                }
                if (currentDay >= 7) {
                    var matchWinnerName = ""
                    var matchWinnerLogo = ""

                    var previousWinnerName = ""
                    var previousWinnerLogo = ""

                    if (playoffScoreList[4].topTeam == 2) {
                        matchWinnerName = playoffScoreList[4].topTeamName
                        matchWinnerLogo = playoffScoreList[4].topTeamLogo
                    } else {
                        matchWinnerName = playoffScoreList[4].bottomTeamName
                        matchWinnerLogo = playoffScoreList[4].bottomTeamLogo
                    }

                    if (playoffScoreList[3].topTeam == 2) {
                        previousWinnerName = playoffScoreList[3].topTeamName
                        previousWinnerLogo = playoffScoreList[3].topTeamLogo
                    } else {
                        previousWinnerName = playoffScoreList[3].bottomTeamName
                        previousWinnerLogo = playoffScoreList[3].bottomTeamLogo
                    }

                    if (playoffScoreList.size < 6) {
                        scope.launch {
                            minorVM.insertMatch(
                                MatchScore(
                                    previousWinnerName,
                                    matchWinnerName,
                                    previousWinnerLogo,
                                    matchWinnerLogo,
                                    0,
                                    0,
                                    PlayOffState.LOWER_BRACKET_FINAL.id
                                )
                            )
                        }
                    }
                }
            } else {
                val teamEnemy = teamStats?.get(currentDay)?.TeamName
                teamEnemy?.let { PrefsHelper.write(PrefsHelper.ENEMY_NAME, it) }
                listener.preMatchClicked()
                PrefsHelper.write(
                    PrefsHelper.TOURNAMENT_COMPETITION,
                    TournamentCompetition.MINOR.id
                )
            }


        }


        nextPlayOff.setOnClickListener {
            if (currentDay == 4) {
                val teamEnemy = if (myGroupPlace == 0) {
                    sortedB[1].TeamName
                } else {
                    sortedB[0].TeamName
                }
                PrefsHelper.write(PrefsHelper.ENEMY_NAME, teamEnemy)
                listener.preMatchClicked()
            } else if (currentDay == 5) {
                val newArray = ArrayList<MatchScore>()
                newArray.add(playoffScoreList[2])
                newArray.add(playoffScoreList[3])
                val enemy = newArray.find {
                    it.topTeamName == PrefsHelper.read(
                        PrefsHelper.TEAM_NAME,
                        ""
                    )
                }?.bottomTeamName ?: ""
                PrefsHelper.write(PrefsHelper.ENEMY_NAME, enemy)
                listener.preMatchClicked()
            } else if (currentDay == 6) {
                val myTeam = PrefsHelper.read(
                    PrefsHelper.TEAM_NAME,
                    ""
                )
                if (playoffScoreList[3].topTeamName == myTeam && playoffScoreList[3].topTeam == 2
                ) {
                    qualifideClosedQuali()
                } else if (playoffScoreList[4].topTeamName == myTeam || playoffScoreList[4].bottomTeamName == myTeam) {
                    if (playoffScoreList[4].topTeamName == myTeam) {
                        PrefsHelper.write(
                            PrefsHelper.ENEMY_NAME,
                            playoffScoreList[4].bottomTeamName
                        )
                    } else {
                        PrefsHelper.write(PrefsHelper.ENEMY_NAME, playoffScoreList[4].topTeamName)
                    }
                    listener.preMatchClicked()
                } else {
                    loseClosedQuali()
                }
            } else if (currentDay == 7) {
                val myTeam = PrefsHelper.read(
                    PrefsHelper.TEAM_NAME,
                    ""
                )
                if ((playoffScoreList[4].topTeamName == myTeam && playoffScoreList[4].topTeam == 2) ||
                    (playoffScoreList[4].bottomTeamName == myTeam && playoffScoreList[4].bottomTeam == 2)
                ) {
                    qualifideClosedQuali()
                } else {
                    loseClosedQuali()
                }
            }
            PrefsHelper.write(PrefsHelper.TOURNAMENT_COMPETITION, TournamentCompetition.MINOR.id)
        }

    }

    private fun loseClosedQuali() {
        requireActivity().showDotaDialog(
            "Вы понинули закрытую квалификацию",
            "Возвращайтесь к тренировкам и улучшайте свою игру",
            "вернуться к тренировкам"
        )
    }

    private fun qualifideClosedQuali() {
        requireActivity().showDotaDialog(
            "Вы выйграли закрытую квалификацию",
            "Возвращайтесь к тренировкам и улучшайте свою игру",
            "вернуться к тренировкам"
        )
    }

    private fun initViews() {
        groupAName = arrayOf(
            groupLayoutA.group1.TeamName,
            groupLayoutA.group2.TeamName,
            groupLayoutA.group3.TeamName,
            groupLayoutA.group4.TeamName
        )
        groupAWin = arrayOf(
            groupLayoutA.group1.ScoreWin,
            groupLayoutA.group2.ScoreWin,
            groupLayoutA.group3.ScoreWin,
            groupLayoutA.group4.ScoreWin
        )

        groupALose = arrayOf(
            groupLayoutA.group1.ScoreLose,
            groupLayoutA.group2.ScoreLose,
            groupLayoutA.group3.ScoreLose,
            groupLayoutA.group4.ScoreLose
        )

        groupALogo = arrayOf(
            groupLayoutA.group1.Teamlogo,
            groupLayoutA.group2.Teamlogo,
            groupLayoutA.group3.Teamlogo,
            groupLayoutA.group4.Teamlogo
        )


        groupBName = arrayOf(
            groupLayoutB.group1.TeamName,
            groupLayoutB.group2.TeamName,
            groupLayoutB.group3.TeamName,
            groupLayoutB.group4.TeamName
        )
        groupBWin = arrayOf(
            groupLayoutB.group1.ScoreWin,
            groupLayoutB.group2.ScoreWin,
            groupLayoutB.group3.ScoreWin,
            groupLayoutB.group4.ScoreWin
        )

        groupBLose = arrayOf(
            groupLayoutB.group1.ScoreLose,
            groupLayoutB.group2.ScoreLose,
            groupLayoutB.group3.ScoreLose,
            groupLayoutB.group4.ScoreLose
        )

        groupBLogo = arrayOf(
            groupLayoutB.group1.Teamlogo,
            groupLayoutB.group2.Teamlogo,
            groupLayoutB.group3.Teamlogo,
            groupLayoutB.group4.Teamlogo
        )


    }

    private fun setImageIn(image: String, imageView: ImageView) {
        Glide.with(this)
            .load(image)
            .into(imageView)
    }

    private fun initGroupColors() {
        groupLayoutA.group1.setBackgroundColor(resources.getColor(R.color.high_green_group))
        groupLayoutA.group2.setBackgroundColor(resources.getColor(R.color.high_green_group))
        groupLayoutA.group3.setBackgroundColor(resources.getColor(R.color.red_group))
        groupLayoutA.group4.setBackgroundColor(resources.getColor(R.color.red_group))

        groupLayoutA.group1.place.text = "1."
        groupLayoutA.group2.place.text = "2."
        groupLayoutA.group3.place.text = "3."
        groupLayoutA.group4.place.text = "4."

        groupLayoutB.group1.setBackgroundColor(resources.getColor(R.color.high_green_group))
        groupLayoutB.group2.setBackgroundColor(resources.getColor(R.color.high_green_group))
        groupLayoutB.group3.setBackgroundColor(resources.getColor(R.color.red_group))
        groupLayoutB.group4.setBackgroundColor(resources.getColor(R.color.red_group))


        groupLayoutB.group1.place.text = "1."
        groupLayoutB.group2.place.text = "2."
        groupLayoutB.group3.place.text = "3."
        groupLayoutB.group4.place.text = "4."
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
            ),
            Pair(
                PlayoffTeam(
                    playoff.team11.teamImage,
                    playoff.team11.teamName,
                    playoff.team11.scoreTeam
                ),
                PlayoffTeam(
                    playoff.team12.teamImage,
                    playoff.team12.teamName,
                    playoff.team12.scoreTeam
                )
            )
        )
    }
}