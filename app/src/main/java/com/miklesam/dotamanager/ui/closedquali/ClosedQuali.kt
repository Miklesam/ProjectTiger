package com.miklesam.dotamanager.ui.closedquali

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.room.teams.TeamsList
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.showCustomToast
import com.miklesam.dotamanager.utils.showDotaDialog
import kotlinx.android.synthetic.main.fragment_closed_quali.*
import kotlinx.android.synthetic.main.group_stage_layout.view.*
import kotlinx.android.synthetic.main.team_in_group_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ClosedQuali : Fragment(R.layout.fragment_closed_quali) {

    var teams: List<Team>? = null
    var teamStats: List<TournamentTeam>? = null
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    interface ClosedQualListener {
        fun preMatchClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val closedVM = ViewModelProviders.of(this).get(ClosedQualiVM::class.java)
        val listener = activity as ClosedQualListener
        val day = "Day ${PrefsHelper.read(PrefsHelper.CLOSED_QUALI_DAY, "1")}"
        closedDay.text = day
        closedVM?.getTeams()?.observe(this, Observer {
            teams = it
            if (!teams.isNullOrEmpty()) {

                closedVM.getTournamentTeams().observe(this, Observer {
                    if (it.isEmpty()) {
                        val tournamentsArr = ArrayList<TournamentTeam>()
                        val yourTeam = PrefsHelper.read(PrefsHelper.TEAM_NAME, "")
                        tournamentsArr.add(
                            TournamentTeam(
                                yourTeam ?: "your Team",
                                TeamsList.CATEGORY_IMAGE_DIR+"yourteamlogo",
                                0,
                                0
                            )
                        )
                        for (team in this!!.teams!!) {
                            tournamentsArr.add(TournamentTeam(team.teamName, team.teamLogo, 0, 0))
                        }
                        scope.launch {
                            closedVM.initTournamentsTeams(tournamentsArr)
                        }
                        showCustomToast("Init succed", Toast.LENGTH_SHORT)
                    } else {
                        if (!it.isNullOrEmpty()) {
                            teamStats = it
                            val gropAteams=it.subList(0,5)
                            val gropBteams=it.subList(5,10)
                            val sortedA=gropAteams.sortedByDescending { it.win }
                            val sortedB=gropBteams.sortedByDescending { it.win }
                            groupLayoutA.group1.ScoreWin.text = sortedA[0].win.toString()
                            groupLayoutA.group2.ScoreWin.text = sortedA[1].win.toString()
                            groupLayoutA.group3.ScoreWin.text = sortedA[2].win.toString()
                            groupLayoutA.group4.ScoreWin.text = sortedA[3].win.toString()
                            groupLayoutA.group5.ScoreWin.text = sortedA[4].win.toString()

                            groupLayoutA.group1.ScoreLose.text = sortedA[0].lose.toString()
                            groupLayoutA.group2.ScoreLose.text = sortedA[1].lose.toString()
                            groupLayoutA.group3.ScoreLose.text = sortedA[2].lose.toString()
                            groupLayoutA.group4.ScoreLose.text = sortedA[3].lose.toString()
                            groupLayoutA.group5.ScoreLose.text = sortedA[4].lose.toString()

                            groupLayoutB.group1.setBackgroundColor(resources.getColor(R.color.high_green_group))
                            groupLayoutB.group2.setBackgroundColor(resources.getColor(R.color.high_green_group))
                            groupLayoutB.group3.setBackgroundColor(resources.getColor(R.color.orange_group))
                            groupLayoutB.group4.setBackgroundColor(resources.getColor(R.color.red_group))
                            groupLayoutB.group5.setBackgroundColor(resources.getColor(R.color.red_group))


                            groupLayoutB.group1.ScoreWin.text = sortedB[0].win.toString()
                            groupLayoutB.group2.ScoreWin.text = sortedB[1].win.toString()
                            groupLayoutB.group3.ScoreWin.text = sortedB[2].win.toString()
                            groupLayoutB.group4.ScoreWin.text = sortedB[3].win.toString()
                            groupLayoutB.group5.ScoreWin.text = sortedB[4].win.toString()

                            groupLayoutB.group1.ScoreLose.text = sortedB[0].lose.toString()
                            groupLayoutB.group2.ScoreLose.text = sortedB[1].lose.toString()
                            groupLayoutB.group3.ScoreLose.text = sortedB[2].lose.toString()
                            groupLayoutB.group4.ScoreLose.text = sortedB[3].lose.toString()
                            groupLayoutB.group5.ScoreLose.text = sortedB[4].lose.toString()


                            groupLayoutA.group1.TeamName.text = sortedA[0].TeamName
                            groupLayoutA.group2.TeamName.text = sortedA[1].TeamName
                            groupLayoutA.group3.TeamName.text = sortedA[2].TeamName
                            groupLayoutA.group4.TeamName.text = sortedA[3].TeamName
                            groupLayoutA.group5.TeamName.text = sortedA[4].TeamName
                            Glide.with(this)
                                .load(sortedA[0].logo)
                                .into(groupLayoutA.group1.Teamlogo)
                            Glide.with(this)
                                .load(sortedA[1].logo)
                                .into(groupLayoutA.group2.Teamlogo)
                            Glide.with(this)
                                .load(sortedA[2].logo)
                                .into(groupLayoutA.group3.Teamlogo)
                            Glide.with(this)
                                .load(sortedA[3].logo)
                                .into(groupLayoutA.group4.Teamlogo)
                            Glide.with(this)
                                .load(sortedA[4].logo)
                                .into(groupLayoutA.group5.Teamlogo)

                            Glide.with(this)
                                .load(sortedB[0].logo)
                                .into(groupLayoutB.group1.Teamlogo)
                            Glide.with(this)
                                .load(sortedB[1].logo)
                                .into(groupLayoutB.group2.Teamlogo)
                            Glide.with(this)
                                .load(sortedB[2].logo)
                                .into(groupLayoutB.group3.Teamlogo)
                            Glide.with(this)
                                .load(sortedB[3].logo)
                                .into(groupLayoutB.group4.Teamlogo)
                            Glide.with(this)
                                .load(sortedB[4].logo)
                                .into(groupLayoutB.group5.Teamlogo)


                            groupLayoutB.group1.TeamName.text = sortedB[0].TeamName
                            groupLayoutB.group2.TeamName.text = sortedB[1].TeamName
                            groupLayoutB.group3.TeamName.text = sortedB[2].TeamName
                            groupLayoutB.group4.TeamName.text = sortedB[3].TeamName
                            groupLayoutB.group5.TeamName.text = sortedB[4].TeamName

                        }

                    }
                })
            }

            Log.w("Teams Hello", it.toString())
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


        })
        playGame.setOnClickListener {

            activity!!.showDotaDialog("Вы понинули закрытую квалификацию","Возвращайтесь к тренировкам и улучшайте свою игру","вернуться к тренировкам")

            //val currebtClosedDay= PrefsHelper.read(PrefsHelper.CLOSED_QUALI_DAY,"1")?.toInt()?:1
            //val teamEnemy = teamStats?.get(currebtClosedDay)?.TeamName
            //teamEnemy?.let { PrefsHelper.write(PrefsHelper.ENEMY_NAME, it) }
            //listener.preMatchClicked()
            /*
            val updatedTeams=teamStats
            updatedTeams!![0].win= (teamStats?.get(0)?.win ?: 0) +1

            scope.launch {
                closedVM.updateTeams(updatedTeams)
            }

             */
        }

    }
}