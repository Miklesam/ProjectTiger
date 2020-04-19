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

    private var players:List<Player> = ArrayList()
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
        Glide.with(holder.itemView.context)
            .load(currentPlayer.flag)
            .into(holder.flag)
        Glide.with(holder.itemView.context)
            .load(currentPlayer.photo)
            .into(holder.photo)
        ViewCompat.setTransitionName(holder.photo, position.toString() + "_photo")
        ViewCompat.setTransitionName(holder.name, position.toString() + "_name")
        ViewCompat.setTransitionName(holder.position, position.toString() + "_position")
        ViewCompat.setTransitionName(holder.cost, position.toString() + "_cost")
    }

    fun setPlayers(playersSet:List<Player>){
        players=playersSet
        notifyDataSetChanged()
        //playerOld=players
        //players=playersSet
        //updatelist(playerOld,players)
    }

    private fun updatelist( old:List<Player>,new:List<Player>){
        val callback = DiffCallback(old, new)
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
    }
}