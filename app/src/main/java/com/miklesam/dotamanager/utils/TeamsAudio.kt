package com.miklesam.dotamanager.utils

import com.miklesam.dotamanager.R

enum class TeamsAudio (val teamName:String, val voice:Int){
    NAVI("NaVi", R.raw.navi),
    GAMBIT("Gambit",R.raw.gambit_esports),
    VIRTUS_PRO("Virtus Pro",R.raw.virtus_pro),
    TEAM_SECRET("Team Secret",R.raw.team_secret),
    VICI_GAMING("Vici Gaming",R.raw.vici_gaming),
    EVI_GENIUSES("Evil Geniuses",R.raw.evil_geniusis),
    ALLINCE("Alliance",R.raw.alliance),
    TNC("TNC",R.raw.tnc),
    LIQUID("Liquid",R.raw.liquid);
}