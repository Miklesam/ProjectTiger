package com.miklesam.dotamanager.ui.minorquali

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.ui.prematch.PreMatchRepo
import com.miklesam.dotamanager.ui.teams.TeamsRepository
import com.miklesam.dotamanager.utils.PrefsHelper

class MinorQualiVM (application: Application) : AndroidViewModel(application){
    private var repository: TeamsRepository = TeamsRepository(application)
    private var preMatchRepo: PreMatchRepo = PreMatchRepo(application)
    private val team1=PrefsHelper.read(PrefsHelper.MINOR_QUALI1,"")?:""
    private val team2=PrefsHelper.read(PrefsHelper.MINOR_QUALI2,"")?:""
    private val team3=PrefsHelper.read(PrefsHelper.MINOR_QUALI3,"")?:""
    private val team4=PrefsHelper.read(PrefsHelper.MINOR_QUALI4,"")?:""


    fun getMinorTeams(): LiveData<List<Team>> {
        return repository.getTeamsByName(listOf(team1,team2,team3,team4))
    }
    fun getScore(): LiveData<List<MatchScore>> {
        return preMatchRepo.getScore()
    }
    fun insertMatch(match:MatchScore){
        preMatchRepo.insertScore(match)
    }
}