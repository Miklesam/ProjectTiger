package com.miklesam.dotamanager.simplefragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.ui.team.TeamViewModel
import com.miklesam.dotamanager.utils.DataConverter
import com.miklesam.dotamanager.utils.PrefsHelper
import kotlinx.android.synthetic.main.fragment_about.*

class FragmentTeamSigning :Fragment(R.layout.fragment_about){

    private var teamViewModel: TeamViewModel?=null

    interface gotoLobby{
        fun toLobby()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamViewModel= ViewModelProviders.of(this).get(TeamViewModel::class.java)
        teamViewModel?.getPlayer()?.observe(this, Observer {
            teamSigning.setPlayer(
                BitmapDrawable(resources,DataConverter.convertByteArray2Image(it[0].photo)),
                BitmapDrawable(resources,DataConverter.convertByteArray2Image(it[1].photo)),
                BitmapDrawable(resources,DataConverter.convertByteArray2Image(it[2].photo)),
                BitmapDrawable(resources,DataConverter.convertByteArray2Image(it[3].photo)),
                BitmapDrawable(resources,DataConverter.convertByteArray2Image(it[4].photo)))
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = activity as gotoLobby
        answer1.setOnClickListener { listener.toLobby() }
        answer2.setOnClickListener { listener.toLobby() }

        val teamNaming=PrefsHelper.read(PrefsHelper.TEAM_NAME,"")
        teamNameText.text="Команда $teamNaming \n подписывает перспективный состав"
    }

}