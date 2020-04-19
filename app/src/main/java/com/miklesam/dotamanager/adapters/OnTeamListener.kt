package com.miklesam.dotamanager.adapters

import androidx.recyclerview.widget.RecyclerView

interface OnTeamListener {
    fun onTeamClick(position: Int,holder:RecyclerView.ViewHolder)
}