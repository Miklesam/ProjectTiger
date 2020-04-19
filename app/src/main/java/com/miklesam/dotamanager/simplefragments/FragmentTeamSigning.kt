package com.miklesam.dotamanager.simplefragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.ui.team.TeamViewModel
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
             val inputStream1 =  context?.contentResolver?.openInputStream(it[0].photo.toUri())
             val player1 = Drawable.createFromStream(inputStream1, it[0].photo)
            val inputStream2 =  context?.contentResolver?.openInputStream(it[1].photo.toUri())
            val player2 = Drawable.createFromStream(inputStream2, it[1].photo)
            val inputStream3 =  context?.contentResolver?.openInputStream(it[2].photo.toUri())
            val player3 = Drawable.createFromStream(inputStream3, it[2].photo)
            val inputStream4 =  context?.contentResolver?.openInputStream(it[3].photo.toUri())
            val player4 = Drawable.createFromStream(inputStream4, it[3].photo)
            val inputStream5 =  context?.contentResolver?.openInputStream(it[4].photo.toUri())
            val player5 = Drawable.createFromStream(inputStream5, it[4].photo)
            teamSigning.setPlayer(player1,
                player2,
                player3,
                player4,
                player5)
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