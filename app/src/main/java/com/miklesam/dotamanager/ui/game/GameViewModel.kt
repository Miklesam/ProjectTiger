package com.miklesam.dotamanager.ui.game

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.dotamanager.datamodels.HeroStats

class GameViewModel : ViewModel(){
    private val allPlayersStats = MutableLiveData<List<String>>()
    fun getPlayersMatchStatistic(): LiveData<List<String>> = allPlayersStats
    val RadiantTeam= arrayListOf<HeroStats>(HeroStats(0,0,0),
        HeroStats(0,0,0),HeroStats(0,0,0),HeroStats(0,0,0),
        HeroStats(0,0,0))
    val DireTeam= arrayListOf<HeroStats>(HeroStats(0,0,0),
        HeroStats(0,0,0),HeroStats(0,0,0),HeroStats(0,0,0),
        HeroStats(0,0,0))
    init {


    }

    fun setStats(int: Int){


        RadiantTeam[0].kills=1
        RadiantTeam[2].assist=1
        DireTeam[3].death=1
        allPlayersStats.value=assignStats()
    }

    private fun assignStats():List<String>{
        val r1="${RadiantTeam[0].kills}/${RadiantTeam[0].death}/${RadiantTeam[0].assist}"
        val r2="${RadiantTeam[1].kills}/${RadiantTeam[1].death}/${RadiantTeam[1].assist}"
        val r3="${RadiantTeam[2].kills}/${RadiantTeam[2].death}/${RadiantTeam[2].assist}"
        val r4="${RadiantTeam[3].kills}/${RadiantTeam[3].death}/${RadiantTeam[3].assist}"
        val r5="${RadiantTeam[4].kills}/${RadiantTeam[4].death}/${RadiantTeam[4].assist}"

        val d1="${DireTeam[0].kills}/${DireTeam[0].death}/${DireTeam[0].assist}"
        val d2="${DireTeam[1].kills}/${DireTeam[1].death}/${DireTeam[1].assist}"
        val d3="${DireTeam[2].kills}/${DireTeam[2].death}/${DireTeam[2].assist}"
        val d4="${DireTeam[3].kills}/${DireTeam[3].death}/${DireTeam[3].assist}"
        val d5="${DireTeam[4].kills}/${DireTeam[4].death}/${DireTeam[4].assist}"

        val totalRadiantKills=RadiantTeam.map { it.kills }.sum().toString()
        val totalDireKills=DireTeam.map { it.kills }.sum().toString()
      return  listOf(r1,r2,r3,r4,r5,d1,d2,d3,d4,d5,totalRadiantKills,totalDireKills)
    }

    fun calculateLineAssign(position:Array<Int>){
        Log.w("GameViewModel",position[0].toString())
        Log.w("GameViewModel2",position[9].toString())

    }

}