package com.miklesam.dotamanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.datamodels.Team
import java.util.ArrayList

class TeamAdapter(val teamListener: OnTeamListener) : RecyclerView.Adapter<TeamHolder>() {

    private var teams:List<Team> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_item,parent,false)
        return TeamHolder(itemView,teamListener)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        val currentTeam: Team= teams.get(position)
        holder.teamLogo.setImageResource(currentTeam.teamLogo)
        holder.teamName.text=currentTeam.teamName
    }

    fun setTeams(teamsSet:List<Team>){
        teams=teamsSet
        notifyDataSetChanged()
        //playerOld=players
        //players=playersSet
        //updatelist(playerOld,players)
    }

    private fun updatelist( old:List<Team>,new:List<Team>){
        //val callback = DiffCallback(old, new)
        //DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
    }
}