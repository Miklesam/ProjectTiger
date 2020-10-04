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

class TeamWithPlayersAdapter() : RecyclerView.Adapter<TeamWithPlayersHolder>() {

    private var teams: List<Team> = ArrayList()
    private var players: List<Player> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamWithPlayersHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_with_player_item, parent, false)
        return TeamWithPlayersHolder(itemView)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamWithPlayersHolder, position: Int) {
        val currentTeam: Team = teams.get(position)
        Glide.with(holder.itemView.context)
            .load(currentTeam.teamLogo)
            .into(holder.teamLogo)
        holder.teamName.text = currentTeam.teamName

        val player1 = players.find { it.nickname == currentTeam.playerPosition1 }
        val player2 = players.find { it.nickname == currentTeam.playerPosition2 }
        val player3 = players.find { it.nickname == currentTeam.playerPosition3 }
        val player4 = players.find { it.nickname == currentTeam.playerPosition4 }
        val player5 = players.find { it.nickname == currentTeam.playerPosition5 }
        Glide.with(holder.itemView.context)
            .load(player1?.photo)
            .into(holder.player1)
        Glide.with(holder.itemView.context)
            .load(player2?.photo)
            .into(holder.player2)
        Glide.with(holder.itemView.context)
            .load(player3?.photo)
            .into(holder.player3)
        Glide.with(holder.itemView.context)
            .load(player4?.photo)
            .into(holder.player4)
        Glide.with(holder.itemView.context)
            .load(player5?.photo)
            .into(holder.player5)

    }

    fun setTeams(teamsSet: List<Team>) {
        teams = teamsSet
        notifyDataSetChanged()
        //playerOld=players
        //players=playersSet
        //updatelist(playerOld,players)
    }

    fun setPlayers(playerSet: List<Player>) {
        players = playerSet
        notifyDataSetChanged()
        //playerOld=players
        //players=playersSet
        //updatelist(playerOld,players)
    }
}