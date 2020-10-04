package com.miklesam.dotamanager.ui.team

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.adapters.TeamPlayersAdapter
import com.miklesam.dotamanager.adapters.TeamWithPlayersAdapter
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.simplefragments.DescriptionViewModel
import com.miklesam.dotamanager.utils.PrefsHelper

class FragmentTeam : Fragment(R.layout.fragment_team), OnPlayerListener {
    var recycler: RecyclerView? = null
    private val teamViewModel by viewModels<TeamViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.teamRecycler)
        recycler?.layoutManager = LinearLayoutManager(requireContext())
        recycler?.setHasFixedSize(true)
        val adapter = TeamWithPlayersAdapter()
        recycler?.adapter = adapter

        val position1 = PrefsHelper.read(PrefsHelper.POSITION_1, "")
        val position2 = PrefsHelper.read(PrefsHelper.POSITION_2, "")
        val position3 = PrefsHelper.read(PrefsHelper.POSITION_3, "")
        val position4 = PrefsHelper.read(PrefsHelper.POSITION_4, "")
        val position5 = PrefsHelper.read(PrefsHelper.POSITION_5, "")
        val teamName = PrefsHelper.read(PrefsHelper.TEAM_NAME, "") ?: ""


        teamViewModel.getPlayer().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.setPlayers(it)
            }
        })

        teamViewModel.getTeams().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val yourTeam = Team(
                    teamName,
                    "",
                    "",
                    position1,
                    position2,
                    position3,
                    position4,
                    position5,
                    ""
                )
                val teamsArray = arrayListOf(yourTeam)
                teamsArray.addAll(it)
                adapter.setTeams(teamsArray)
            }
        })
    }

    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("asdasdas", "asdasdqq")
    }
}