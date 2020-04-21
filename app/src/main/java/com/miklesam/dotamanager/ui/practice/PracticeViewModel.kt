package com.miklesam.dotamanager.ui.practice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.ui.market.MarketRepository
import com.miklesam.dotamanager.ui.teams.TeamsRepository
import com.miklesam.dotamanager.utils.PrefsHelper

class PracticeViewModel(application: Application) : AndroidViewModel(application) {
    val pos1: String
    val pos2: String
    val pos3: String
    val pos4: String
    val pos5: String

    init {
        pos1 = PrefsHelper.read(PrefsHelper.POSITION_1, "").toString()
        pos2 = PrefsHelper.read(PrefsHelper.POSITION_2, "").toString()
        pos3 = PrefsHelper.read(PrefsHelper.POSITION_3, "").toString()
        pos4 = PrefsHelper.read(PrefsHelper.POSITION_4, "").toString()
        pos5 = PrefsHelper.read(PrefsHelper.POSITION_5, "").toString()
    }
    private var repository: MarketRepository = MarketRepository(application)
    private var teamRpository: TeamsRepository = TeamsRepository(application)
    fun getPlayer(): LiveData<List<Player>> {
        return repository.getPlayerByName(listOf(pos1, pos2, pos3, pos4, pos5))
    }
    fun getTeams(): LiveData<List<Team>> {
        return teamRpository.getTeams()
    }

}