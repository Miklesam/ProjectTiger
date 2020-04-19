package com.miklesam.dotamanager.ui.team

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.adapters.TeamPlayersAdapter

class FragmentTeam :Fragment(R.layout.fragment_team), OnPlayerListener {
    private var teamViewModel: TeamViewModel?=null
    var recycler: RecyclerView?=null
    var adapter : TeamPlayersAdapter?=null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler=view.findViewById(R.id.teamRecycler)
        recycler?.layoutManager = GridLayoutManager(context,5)
        recycler?.setHasFixedSize(true)
        adapter = TeamPlayersAdapter(context,this)
        recycler?.adapter = adapter

        teamViewModel= ViewModelProviders.of(this).get(TeamViewModel::class.java)
        teamViewModel?.getPlayer()?.observe(this, Observer {
            adapter?.setPlayers(it)

        })
    }

    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("asdasdas","asdasdqq")
    }
}