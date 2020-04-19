package com.miklesam.dotamanager.adapters

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Player
import java.util.ArrayList

class TeamPlayersAdapter(val context : Context?,playerListener: OnPlayerListener) : RecyclerView.Adapter<TeamPlayerHolder>() {

    private var players:List<Player> = ArrayList()
    private val mOnPlayerListener=playerListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPlayerHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_player_item,parent,false);
        return TeamPlayerHolder(itemView,mOnPlayerListener)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: TeamPlayerHolder, position: Int) {
        val currentPlayer: Player= players.get(position)
        holder.nickName.text=currentPlayer.nickname
        holder.name.text=currentPlayer.name
        Glide.with(holder.itemView.context)
            .load(currentPlayer.photo)
            .into(holder.photo)
        val inputStream =  holder.itemView.context.contentResolver.openInputStream(currentPlayer.flag.toUri())
        val drawable = Drawable.createFromStream(inputStream, currentPlayer.flag)
        holder.backGround.background=drawable
        ViewCompat.setTransitionName(holder.photo, position.toString() + "_photo")
        ViewCompat.setTransitionName(holder.name, position.toString() + "_name")
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