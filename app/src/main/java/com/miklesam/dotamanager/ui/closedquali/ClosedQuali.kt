package com.miklesam.dotamanager.ui.closedquali

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.utils.PrefsHelper
import kotlinx.android.synthetic.main.fragment_closed_quali.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_teams_profile.view.*
import kotlinx.android.synthetic.main.group_stage_layout.view.*
import kotlinx.android.synthetic.main.team_in_group_layout.view.*

class ClosedQuali : Fragment(R.layout.fragment_closed_quali) {

    var teams: List<Team>? = null

    interface ClosedQualListener {
        fun preMatchClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        val closedVM = ViewModelProviders.of(this).get(ClosedQualiVM::class.java)
        val listener = activity as ClosedQualListener

        closedVM?.getTeams()?.observe(this, Observer {
            teams = it
            Log.w("Teams Hello", it.toString())
            groupLayoutA.group1.setBackgroundColor(resources.getColor(R.color.high_green_group))
            groupLayoutA.group2.setBackgroundColor(resources.getColor(R.color.high_green_group))
            groupLayoutA.group3.setBackgroundColor(resources.getColor(R.color.orange_group))
            groupLayoutA.group4.setBackgroundColor(resources.getColor(R.color.red_group))
            groupLayoutA.group5.setBackgroundColor(resources.getColor(R.color.red_group))


            groupLayoutA.group1.place.text="1."
            groupLayoutA.group2.place.text="2."
            groupLayoutA.group3.place.text="3."
            groupLayoutA.group4.place.text="4."
            groupLayoutA.group5.place.text="5."

            groupLayoutB.group1.setBackgroundColor(resources.getColor(R.color.high_green_group))
            groupLayoutB.group2.setBackgroundColor(resources.getColor(R.color.high_green_group))
            groupLayoutB.group3.setBackgroundColor(resources.getColor(R.color.orange_group))
            groupLayoutB.group4.setBackgroundColor(resources.getColor(R.color.red_group))
            groupLayoutB.group5.setBackgroundColor(resources.getColor(R.color.red_group))


            groupLayoutB.group1.place.text="1."
            groupLayoutB.group2.place.text="2."
            groupLayoutB.group3.place.text="3."
            groupLayoutB.group4.place.text="4."
            groupLayoutB.group5.place.text="5."



            groupLayoutA.group1.TeamName.text = it[0].teamName
            groupLayoutA.group2.TeamName.text = it[1].teamName
            groupLayoutA.group3.TeamName.text = it[2].teamName
            groupLayoutA.group4.TeamName.text = it[3].teamName
            groupLayoutA.group5.TeamName.text = PrefsHelper.read(PrefsHelper.TEAM_NAME, "")
            Glide.with(this)
                .load(it[0].teamLogo)
                .into(groupLayoutA.group1.Teamlogo)
            Glide.with(this)
                .load(it[1].teamLogo)
                .into(groupLayoutA.group2.Teamlogo)
            Glide.with(this)
                .load(it[2].teamLogo)
                .into(groupLayoutA.group3.Teamlogo)
            Glide.with(this)
                .load(it[3].teamLogo)
                .into(groupLayoutA.group4.Teamlogo)
            Glide.with(this)
                .load(R.drawable.yourteamlogo)
                .into(groupLayoutA.group5.Teamlogo)

            Glide.with(this)
                .load(it[4].teamLogo)
                .into(groupLayoutB.group1.Teamlogo)
            Glide.with(this)
                .load(it[5].teamLogo)
                .into(groupLayoutB.group2.Teamlogo)
            Glide.with(this)
                .load(it[6].teamLogo)
                .into(groupLayoutB.group3.Teamlogo)
            Glide.with(this)
                .load(it[7].teamLogo)
                .into(groupLayoutB.group4.Teamlogo)
            Glide.with(this)
                .load(it[8].teamLogo)
                .into(groupLayoutB.group5.Teamlogo)


            groupLayoutB.group1.TeamName.text = it[4].teamName
            groupLayoutB.group2.TeamName.text = it[5].teamName
            groupLayoutB.group3.TeamName.text = it[6].teamName
            groupLayoutB.group4.TeamName.text = it[7].teamName
            groupLayoutB.group5.TeamName.text = it[8].teamName
        })
        playGame.setOnClickListener {
            val teamEnemy = teams?.get(0)?.teamName
            teamEnemy?.let { PrefsHelper.write(PrefsHelper.ENEMY_NAME, it) }
            listener.preMatchClicked()
        }


    }
}