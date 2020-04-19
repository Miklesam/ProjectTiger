package com.miklesam.dotamanager.ui.teams

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.OnTeamListener
import com.miklesam.dotamanager.adapters.TeamAdapter
import com.miklesam.dotamanager.datamodels.Team

class FragmentTeams :Fragment(R.layout.fragment_teams),OnTeamListener{

    interface teamShow{
        fun teamsClicked(teamsNickNames:ArrayList<String?>,teamName:String)
    }
    var teamListener:teamShow?=null
    private var teamsViewModel: TeamsViewModel?=null
    var teams: List<Team>? = null
    var recycler: RecyclerView?=null
    var adapter : TeamAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsViewModel= ViewModelProviders.of(this).get(TeamsViewModel::class.java)
        teamListener = activity as teamShow
        recycler=view.findViewById(R.id.recyclerTeams)
        recycler?.layoutManager = LinearLayoutManager(context)
        recycler?.setHasFixedSize(true)
        adapter = TeamAdapter(this)
        recycler?.adapter = adapter

        teamsViewModel?.getTeams()?.observe(this, Observer {
            Log.w("Teams Hello",it.toString())
            teams=it
            adapter?.setTeams(it)
        })
    }

    override fun onTeamClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("Team Cleaced","clicked $position")
        val team= teams?.get(position)
        val teamsNickNames= arrayListOf(team?.playerPosition1,team?.playerPosition2,team?.playerPosition3,team?.playerPosition4,team?.playerPosition5)
        teamListener?.teamsClicked(teamsNickNames, team!!.teamName)
    }
}