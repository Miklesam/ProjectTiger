package com.miklesam.dotamanager.adapters

import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.player_item.view.*
import kotlinx.android.synthetic.main.team_generate_item.view.*
import kotlinx.android.synthetic.main.team_item.view.*
import kotlinx.android.synthetic.main.team_item.view.teamLogo
import kotlinx.android.synthetic.main.team_item.view.teamName

class TeamGenerateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val teamLogo: ImageView
    val teamName: TextView
    val player1: TextView
    val player2: TextView
    val player3: TextView
    val player4: TextView
    val player5: TextView

    init {
        teamLogo = itemView.teamLogo
        teamName = itemView.teamName
        player1 = itemView.player1
        player2 = itemView.player2
        player3 = itemView.player3
        player4 = itemView.player4
        player5 = itemView.player5
    }
}