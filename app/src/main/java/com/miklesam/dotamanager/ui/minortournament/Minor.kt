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
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.room.teams.TeamsList
import com.miklesam.dotamanager.scope
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.showCustomToast
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_minor.*
import kotlinx.android.synthetic.main.minor_group_stage_layout.view.*
import kotlinx.android.synthetic.main.team_in_group_layout.view.*
import kotlinx.coroutines.launch

class Minor : Fragment(R.layout.fragment_minor){
    private val minorVM: MinorVM by viewModels()
    var teams: List<Team>? = null
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initGroupColors()

        minorVM.getTeams().observe(viewLifecycleOwner, Observer {
            teams=it
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


        })
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
}