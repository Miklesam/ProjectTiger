package com.miklesam.dotamanager.ui.minorquali

import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import com.miklesam.dotamanager.utils.Gone
import com.miklesam.dotamanager.utils.PlayOffState
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.Visible
import kotlinx.android.synthetic.main.closed_playoff_layout.view.*
import kotlinx.android.synthetic.main.closed_playoff_team_layout.view.*
import kotlinx.android.synthetic.main.fragment_closed_quali.*
import kotlinx.android.synthetic.main.fragment_minor_quali.*
import kotlinx.android.synthetic.main.fragment_minor_quali.playGame
import kotlinx.android.synthetic.main.fragment_minor_quali.playoff
import kotlinx.coroutines.launch

class MinorQuali :Fragment(R.layout.fragment_minor_quali){
    private val minorVM: MinorQualiVM by viewModels()
    private lateinit var playoffScoreList: List<MatchScore>
    lateinit var teams: List<Team>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        minorVM.getMinorTeams().observe(viewLifecycleOwner, Observer {listTeam->
            teams=listTeam
            if (listTeam.size>2){
                setImageIn(listTeam[0].teamLogo,team2)
                setImageIn(listTeam[1].teamLogo,team3)
                setImageIn(listTeam[2].teamLogo,team4)
                setImageIn(TeamsList.CATEGORY_IMAGE_DIR + "yourteamlogo",team1)
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
            if(playoffScoreList.isNullOrEmpty()){
                scope.launch {
                    minorVM.insertMatch(
                        MatchScore(
                            PrefsHelper.read(PrefsHelper.TEAM_NAME,"")?:"",
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
            playoff.Visible()
            playGame.Gone()

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

}