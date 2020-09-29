package com.miklesam.dotamanager.adapters

import androidx.recyclerview.widget.RecyclerView

interface OnPlayerChooseListener {
    fun onPlayerClick(position: Int, nickName: String)
}