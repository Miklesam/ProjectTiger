package com.miklesam.dotamanager.ui.minortournament

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.ui.closedquali.ClosedRepository
import com.miklesam.dotamanager.ui.prematch.PreMatchRepo
import com.miklesam.dotamanager.ui.teams.TeamsRepository

class MinorVM(application:Application):AndroidViewModel(application){
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

    fun getScore(): LiveData<List<MatchScore>> {
        return preMatchRepo.getScore()
    }
    fun insertMatch(match:MatchScore){
        preMatchRepo.insertScore(match)
    }

}