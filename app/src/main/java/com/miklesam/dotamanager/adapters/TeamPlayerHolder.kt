package com.miklesam.dotamanager.adapters

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.player_item.view.playerCountryImage
import kotlinx.android.synthetic.main.player_item.view.playerName
import kotlinx.android.synthetic.main.player_item.view.playerNickname
import kotlinx.android.synthetic.main.player_item.view.playerPhoto
import kotlinx.android.synthetic.main.team_player_item.view.*

class TeamPlayerHolder (itemView:View, var playerListener: OnPlayerListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
    override fun onClick(p0: View?) {
        playerListener.onPlayerClick(adapterPosition,this)
    }
    val nickName:TextView
    val name :TextView
    val photo :ImageView
    val backGround :FrameLayout
    init{
        nickName=itemView.playerNickname
        name=itemView.playerName
        photo=itemView.playerPhoto
        backGround=itemView.backFlag
        itemView.setOnClickListener(this)
    }
}