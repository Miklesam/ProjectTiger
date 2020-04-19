package com.miklesam.dotamanager.ui.teamsprofile

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
import kotlinx.android.synthetic.main.fragment_teams_profile.*

class FragmentTeamsProfile : Fragment(R.layout.fragment_teams_profile), OnPlayerListener {

    private var teamProfileViewModel: TeamsProfileViewModel? = null
    var recycler: RecyclerView? = null
    var adapter: TeamPlayersAdapter? = null
    lateinit var players: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamProfileViewModel = ViewModelProviders.of(this).get(TeamsProfileViewModel::class.java)
        if (arguments != null) {
            players = arguments!!.getStringArrayList("players")!!
            val teamNametext = arguments!!.getString("teamName")
            if (teamNametext != null) {
                teamProfileViewModel?.setTeamName(teamNametext)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.teamRecycler)
        recycler?.layoutManager = GridLayoutManager(context, 5)
        recycler?.setHasFixedSize(true)
        adapter = TeamPlayersAdapter(context, this)
        recycler?.adapter = adapter

        teamProfileViewModel?.getPlayer(players)?.observe(this, Observer {
            adapter?.setPlayers(it)
        })
        teamProfileViewModel?.getTeamName()
            ?.observe(this, Observer {
                teamName.text = it
            })
    }

    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("asdasdas", "asdasdqq")
    }
}