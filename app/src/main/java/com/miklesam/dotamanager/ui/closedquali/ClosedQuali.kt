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
import kotlinx.android.synthetic.main.group_stage_layout.view.*

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
            groupLayoutA.Team1Name.text = it[0].teamName
            groupLayoutA.Team2Name.text = it[1].teamName
            groupLayoutA.Team3Name.text = it[2].teamName
            groupLayoutA.Team4Name.text = it[3].teamName
            groupLayoutA.Team5Name.text = PrefsHelper.read(PrefsHelper.TEAM_NAME, "")
            Glide.with(this)
                .load(it[0].teamLogo)
                .into(groupLayoutA.team1Logo)
            Glide.with(this)
                .load(it[1].teamLogo)
                .into(groupLayoutA.team2Logo)
            Glide.with(this)
                .load(it[2].teamLogo)
                .into(groupLayoutA.team3Logo)
            Glide.with(this)
                .load(it[3].teamLogo)
                .into(groupLayoutA.team4Logo)
            Glide.with(this)
                .load(R.drawable.yourteamlogo)
                .into(groupLayoutA.team5Logo)
            groupLayoutA.Team2Name.text = it[1].teamName
            groupLayoutA.Team3Name.text = it[2].teamName
            groupLayoutA.Team4Name.text = it[3].teamName

            Glide.with(this)
                .load(it[4].teamLogo)
                .into(groupLayoutB.team1Logo)
            Glide.with(this)
                .load(it[5].teamLogo)
                .into(groupLayoutB.team2Logo)
            Glide.with(this)
                .load(it[6].teamLogo)
                .into(groupLayoutB.team3Logo)
            Glide.with(this)
                .load(it[7].teamLogo)
                .into(groupLayoutB.team4Logo)
            Glide.with(this)
                .load(it[8].teamLogo)
                .into(groupLayoutB.team5Logo)


            groupLayoutB.Team1Name.text = it[4].teamName
            groupLayoutB.Team2Name.text = it[5].teamName
            groupLayoutB.Team3Name.text = it[6].teamName
            groupLayoutB.Team4Name.text = it[7].teamName
            groupLayoutB.Team5Name.text = it[8].teamName
        })
        playGame.setOnClickListener {
            val teamEnemy = teams?.get(0)?.teamName
            teamEnemy?.let { PrefsHelper.write(PrefsHelper.ENEMY_NAME, it) }
            listener.preMatchClicked()
        }


    }
}