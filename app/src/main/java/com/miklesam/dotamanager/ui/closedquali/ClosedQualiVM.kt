package com.miklesam.dotamanager.ui.closedquali

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.ui.prematch.PreMatchRepo
import com.miklesam.dotamanager.ui.teams.TeamsRepository

class ClosedQualiVM (application: Application) : AndroidViewModel(application){
    private var repository: TeamsRepository = TeamsRepository(application)
    private var repositoryClosed: ClosedRepository = ClosedRepository(application)
    private var preMatchRepo: PreMatchRepo = PreMatchRepo(application)
    fun getTeams(): LiveData<List<Team>> {
        return repository.getTeams()
    }
    fun getTournamentTeams(): LiveData<List<TournamentTeam>> {
        return  repositoryClosed.getTournamentTeams()
    }
    fun initTournamentsTeams(list: List<TournamentTeam>){
        repositoryClosed.initTournamentsTeams(list)
    }
    fun updateTeams(teams:List<TournamentTeam>){
        repositoryClosed.updateTeams(teams)
    }

    fun getScore(): LiveData<List<MatchScore>> {
        return preMatchRepo.getScore()
    }

    fun insertMatch(match:MatchScore){
        preMatchRepo.insertScore(match)
    }

}