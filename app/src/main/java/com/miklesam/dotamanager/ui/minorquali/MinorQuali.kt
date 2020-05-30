package com.miklesam.dotamanager.ui.minorquali

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.PlayoffTeam
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.room.teams.TeamsList
import com.miklesam.dotamanager.scope
import com.miklesam.dotamanager.ui.closedquali.ClosedQuali
import com.miklesam.dotamanager.utils.*
import kotlinx.android.synthetic.main.closed_playoff_layout.*
import kotlinx.android.synthetic.main.closed_playoff_layout.view.*
import kotlinx.android.synthetic.main.closed_playoff_layout.view.nextPlayOff
import kotlinx.android.synthetic.main.closed_playoff_team_layout.view.*
import kotlinx.android.synthetic.main.fragment_closed_quali.*
import kotlinx.android.synthetic.main.fragment_minor_quali.*
import kotlinx.android.synthetic.main.fragment_minor_quali.playGame
import kotlinx.android.synthetic.main.fragment_minor_quali.playoff
import kotlinx.android.synthetic.main.fragment_minor_quali.team1
import kotlinx.android.synthetic.main.fragment_minor_quali.team2
import kotlinx.android.synthetic.main.fragment_minor_quali.team3
import kotlinx.android.synthetic.main.fragment_minor_quali.team4
import kotlinx.coroutines.launch

class MinorQuali : Fragment(R.layout.fragment_minor_quali) {
    private val minorVM: MinorQualiVM by viewModels()
    private lateinit var playoffScoreList: List<MatchScore>
    lateinit var teams: List<Team>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val minorDay = PrefsHelper.read(PrefsHelper.MINOR_QUALI_DAY, "1")?.toInt() ?: 1
        val day = "Day $minorDay"
        minorDayView.text = day
        val listener = activity as ClosedQuali.ClosedQualListener
        super.onViewCreated(view, savedInstanceState)
        minorVM.getMinorTeams().observe(viewLifecycleOwner, Observer { listTeam ->
            teams = listTeam
            if (listTeam.size > 2) {
                setImageIn(listTeam[0].teamLogo, team2)
                setImageIn(listTeam[1].teamLogo, team3)
                setImageIn(listTeam[2].teamLogo, team4)
                setImageIn(TeamsList.CATEGORY_IMAGE_DIR + "yourteamlogo", team1)

                team1Name.text = PrefsHelper.read(PrefsHelper.TEAM_NAME, "")
                team2Name.text = listTeam[0].teamName
                team3Name.text = listTeam[1].teamName
                team4Name.text = listTeam[2].teamName

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


        playGame.setOnClickListener {
            if (playoffScoreList.isNullOrEmpty()) {
                scope.launch {
                    minorVM.insertMatch(
                        MatchScore(
                            PrefsHelper.read(PrefsHelper.TEAM_NAME, "") ?: "",
                            teams[0].teamName,
                            TeamsList.CATEGORY_IMAGE_DIR + "yourteamlogo",
                            teams[0].teamLogo,
                            0,
                            0,
                            PlayOffState.LOWER_BRACKER_R1.id
                        )
                    )
                    minorVM.insertMatch(
                        MatchScore(
                            teams[1].teamName,
                            teams[2].teamName,
                            teams[1].teamLogo,
                            teams[2].teamLogo,
                            0,
                            0,
                            PlayOffState.UPPER_BRACKET_FINAL.id
                        )
                    )
                }
            }
            if (minorDay >= 2) {
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

            if (minorDay >= 3) {
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
            if (minorDay >= 4) {
                var matchWinnerName = ""
                var matchWinnerLogo = ""
                if (playoffScoreList[4].topTeam == 2) {
                    matchWinnerName = playoffScoreList[4].topTeamName
                    matchWinnerLogo = playoffScoreList[4].topTeamLogo
                } else {
                    matchWinnerName = playoffScoreList[4].bottomTeamName
                    matchWinnerLogo = playoffScoreList[4].bottomTeamLogo
                }

                playoff.team12.teamName.text = matchWinnerName
                setImageIn(matchWinnerLogo, playoff.team12.teamImage)
            }

            playoff.Visible()
            playGame.Gone()

        }
        nextPlayOff.setOnClickListener {
            when (PrefsHelper.read(PrefsHelper.MINOR_QUALI_DAY, "1")) {
                "1" -> {
                    PrefsHelper.write(PrefsHelper.ENEMY_NAME, playoffScoreList[0].bottomTeamName)
                    listener.preMatchClicked()
                }
                "2" -> {
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
                }
                "3" -> {
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
                            PrefsHelper.write(
                                PrefsHelper.ENEMY_NAME,
                                playoffScoreList[4].topTeamName
                            )
                        }
                        listener.preMatchClicked()
                    } else {
                        loseClosedQuali()
                    }
                }
                "4" -> {
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
                else -> {

                }
            }
            PrefsHelper.write(
                PrefsHelper.TOURNAMENT_COMPETITION,
                TournamentCompetition.MINIR_QUALI.id
            )
        }


    }

    private fun setImageIn(image: String, imageView: ImageView) {
        Glide.with(this)
            .load(image)
            .into(imageView)
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

    private fun qualifideClosedQuali() {
        requireActivity().showDotaDialog(
            "Вы выйграли закрытую квалификацию",
            "Возвращайтесь к тренировкам и улучшайте свою игру",
            "вернуться к тренировкам"
        )
    }

    private fun loseClosedQuali() {
        requireActivity().showDotaDialog(
            "Вы понинули закрытую квалификацию",
            "Возвращайтесь к тренировкам и улучшайте свою игру",
            "вернуться к тренировкам"
        )
    }

}