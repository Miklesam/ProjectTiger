package com.miklesam.dotamanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Player
import java.util.ArrayList

class MarketAdapter(playerListener: OnPlayerListener) : RecyclerView.Adapter<MarketPlayerHolder>() {

    private var players:List<Player> = ArrayList()
    private var playerOld:List<Player> = ArrayList()
    private val mOnPlayerListener=playerListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketPlayerHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_item,parent,false);
        return MarketPlayerHolder(itemView,mOnPlayerListener)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: MarketPlayerHolder, position: Int) {
        val currentPlayer: Player= players.get(position)
        holder.nickName.text=currentPlayer.nickname
        holder.name.text=currentPlayer.name
        holder.position.text=currentPlayer.position
        holder.cost.text=currentPlayer.cost
        ViewCompat.setTransitionName(holder.photo, position.toString() + "_photo")
    }

    fun setPlayers(playersSet:List<Player>){
        playerOld=players
        players=playersSet
        updatelist(playerOld,players)
    }

    private fun updatelist( old:List<Player>,new:List<Player>){
        val callback = DiffCallback(old, new)
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
    }
}