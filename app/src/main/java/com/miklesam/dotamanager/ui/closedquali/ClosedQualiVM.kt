package com.miklesam.dotamanager.ui.closedquali

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.ui.teams.TeamsRepository

class ClosedQualiVM (application: Application) : AndroidViewModel(application){
    private var repository: TeamsRepository = TeamsRepository(application)
    private var repositoryClosed: ClosedRepository = ClosedRepository(application)
    fun getTeams(): LiveData<List<Team>> {
        return repository.getTeams()
    }
    fun getTournamentTeams(): LiveData<List<TournamentTeam>> {
        return  repositoryClosed.getTournamentTeams()
    }
    fun initTournamentsTeams(list: List<TournamentTeam>){
        repositoryClosed.initTournamentsTeams(list)
    }

}