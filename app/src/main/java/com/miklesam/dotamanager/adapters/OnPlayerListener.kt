package com.miklesam.dotamanager.adapters

import androidx.recyclerview.widget.RecyclerView

interface OnPlayerListener {
    fun onPlayerClick(position: Int,holder:RecyclerView.ViewHolder)
}