package com.miklesam.dotamanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.datamodels.Team
import java.util.ArrayList

class TeamGenerateAdapter() : RecyclerView.Adapter<TeamGenerateHolder>() {

    private var teams: List<Team> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamGenerateHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_generate_item, parent, false)
        return TeamGenerateHolder(itemView)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamGenerateHolder, position: Int) {
        val currentTeam: Team = teams.get(position)
        Glide.with(holder.itemView.context)
            .load(currentTeam.teamLogo)
            .into(holder.teamLogo)
        holder.teamName.text = currentTeam.teamName
        holder.player1.text = "1. ${currentTeam.playerPosition1}"
        holder.player2.text = "2. ${currentTeam.playerPosition2}"
        holder.player3.text = "3. ${currentTeam.playerPosition3}"
        holder.player4.text = "4. ${currentTeam.playerPosition4}"
        holder.player5.text = "5. ${currentTeam.playerPosition5}"
    }

    fun setTeams(teamsSet: List<Team>) {
        teams = teamsSet
        notifyDataSetChanged()
    }

}