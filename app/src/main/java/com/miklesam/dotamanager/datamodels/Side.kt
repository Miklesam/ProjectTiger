package com.miklesam.dotamanager.datamodels

import android.util.Log

data class Side(val mid:ArrayList<Boolean>,val bot:ArrayList<Boolean>,val top:ArrayList<Boolean>){
    val allBuilds= arrayListOf(true,true,true,true,
        true,true,true,true,true,true)

    fun updateAncient(notEnd:Boolean){
        allBuilds.clear()
        allBuilds.addAll(mid)
        if (mid.size<3){
            for(i in 0 until  3-mid.size){
                allBuilds.add(false)
            }
        }
        allBuilds.addAll(bot)
        if (bot.size<3){
            for(i in 0 until  3-bot.size){
                allBuilds.add(false)
            }
        }
        allBuilds.addAll(top)
        if (top.size<3){
            for(i in 0 until  3-top.size){
                allBuilds.add(false)
            }
        }
        allBuilds.add(notEnd)
        Log.w("Side", "side is $allBuilds")
    }
}