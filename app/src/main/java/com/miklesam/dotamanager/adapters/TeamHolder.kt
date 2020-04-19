package com.miklesam.dotamanager.adapters

import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.player_item.view.*
import kotlinx.android.synthetic.main.team_item.view.*

class TeamHolder (itemView:View, var teamListener: OnTeamListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
    override fun onClick(p0: View?) {
        teamListener.onTeamClick(adapterPosition,this)
    }
    val teamLogo :ImageView
    val teamName:TextView
    init{
        teamLogo=itemView.teamLogo
        teamName=itemView.teamName

        itemView.setOnClickListener(this)
    }
}