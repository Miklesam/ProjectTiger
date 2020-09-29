package com.miklesam.dotamanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Player
import java.util.ArrayList

class MarketAdapter(playerListener: OnPlayerListener) : RecyclerView.Adapter<MarketPlayerHolder>() {

    private var players: List<Player>? = ArrayList()
    private var mOnPlayerListener = playerListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketPlayerHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_item, parent, false);
        return MarketPlayerHolder(itemView, mOnPlayerListener)
    }

    override fun getItemCount(): Int {
        return players?.size ?: 0
    }

    override fun onBindViewHolder(holder: MarketPlayerHolder, position: Int) {
        val currentPlayer: Player =
            players?.get(position) ?: Player("", "", "", "", "", 0, 0, 0, "")
        holder.nickName.text = currentPlayer.nickname
        holder.name.text = currentPlayer.name
        //holder.position.text = currentPlayer.position
        if (currentPlayer.currentTeam == null) {
            holder.cost.setTextColor(holder.name.context.resources.getColor(R.color.dark_green))
        } else {
            holder.cost.setTextColor(holder.name.context.resources.getColor(R.color.colorAccent))
        }
        val currentTeam = currentPlayer.currentTeam ?: "Free agent"
        holder.cost.text = currentTeam
        Glide.with(holder.itemView.context)
            .load(currentPlayer.flag)
            .into(holder.flag)
        Glide.with(holder.itemView.context)
            .load(currentPlayer.photo)
            .into(holder.photo)
        ViewCompat.setTransitionName(holder.photo, position.toString() + "_photo")
        ViewCompat.setTransitionName(holder.name, position.toString() + "_name")
        //ViewCompat.setTransitionName(holder.position, position.toString() + "_position")
        ViewCompat.setTransitionName(holder.cost, position.toString() + "_cost")
    }

    fun setPlayers(playersSet: List<Player>) {
        players = playersSet
        notifyDataSetChanged()
        //playerOld=players
        //players=playersSet
        //updatelist(playerOld,players)
    }

    private fun updatelist(old: List<Player>, new: List<Player>) {
        val callback = DiffCallback(old, new)
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
    }
}