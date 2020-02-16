package com.miklesam.dotamanager.adapters

import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.player_item.view.*

class MarketPlayerHolder (itemView:View,var playerListener: OnPlayerListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
    override fun onClick(p0: View?) {
        playerListener.onPlayerClick(adapterPosition)
    }

    val nickName:TextView
    val name :TextView
    val position :TextView
    val cost :TextView
    init{
        nickName=itemView.playerNickname
        name=itemView.playerName
        position=itemView.playerPosition
        cost=itemView.playerCost
        itemView.setOnClickListener(this)
    }
}