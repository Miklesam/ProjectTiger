package com.miklesam.dotamanager.adapters

import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.player_item.view.*
import kotlinx.android.synthetic.main.team_item.view.*
import kotlinx.android.synthetic.main.team_with_player_item.view.*

class TeamWithPlayersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val teamLogo: ImageView
    val teamName: TextView
    val player1: ImageView
    val player2: ImageView
    val player3: ImageView
    val player4: ImageView
    val player5: ImageView

    init {
        teamLogo = itemView.team_logo
        teamName = itemView.team_with_player_name
        player1 = itemView.playerPhoto1
        player2 = itemView.playerPhoto2
        player3 = itemView.playerPhoto3
        player4 = itemView.playerPhoto4
        player5 = itemView.playerPhoto5
    }
}