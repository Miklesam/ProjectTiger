package com.miklesam.dotamanager.ui.practice

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.adapters.OnTeamListener
import com.miklesam.dotamanager.adapters.TeamAdapter
import com.miklesam.dotamanager.adapters.TeamPlayersAdapter
import com.miklesam.dotamanager.datamodels.Team
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_practice.*

class FragmentPractice : Fragment(R.layout.fragment_practice), OnPlayerListener, OnTeamListener {

    private var practiceViewModel: PracticeViewModel?=null
    var recycler: RecyclerView?=null
    var adapter : TeamPlayersAdapter?=null
    var teams: List<Team>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler=view.findViewById(R.id.teamRecycler)
        recycler?.layoutManager = GridLayoutManager(context,5)
        recycler?.setHasFixedSize(true)
        adapter = TeamPlayersAdapter(context,this)
        recycler?.adapter = adapter

        practiceViewModel= ViewModelProviders.of(this).get(PracticeViewModel::class.java)
        practiceViewModel?.getPlayer()?.observe(this, Observer {
            adapter?.setPlayers(it)

        })
        practiceViewModel?.getTeams()?.observe(this, Observer {
            Log.w("Teams Hello",it.toString())
        })

        teamTraining.setOnClickListener {
            //
        }
        soloTraining.setOnClickListener {
            recycler?.layoutManager = GridLayoutManager(context,5)
            recycler?.adapter=adapter
        }


    }
    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("asdasdas","asdasdqq")
    }

    override fun onTeamClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("asdasdas","asdasdqq")
    }
}