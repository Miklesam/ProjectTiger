package com.miklesam.dotamanager.ui.prematch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.ui.closedquali.ClosedRepository
import com.miklesam.dotamanager.ui.teams.TeamsRepository

class PreMatchVM(application: Application) : AndroidViewModel(application){

    private var repository: TeamsRepository = TeamsRepository(application)
    private var repositoryClosed: ClosedRepository = ClosedRepository(application)
    private var preMatchRepository: PreMatchRepo = PreMatchRepo(application)
    private val calculate = MutableLiveData<Boolean>()
    private val radWin = MutableLiveData<Boolean>()
    private val radScore = MutableLiveData<Int>()
    private val direScore = MutableLiveData<Int>()
    fun getState(): LiveData<Boolean> = calculate
    fun getWinner(): LiveData<Boolean> = radWin
    fun getRadScore(): LiveData<Int> = radScore
    fun getDireScore(): LiveData<Int> = direScore



    init{
        //calculate.value=false
        //radWin.value=false
    }



    fun getTeamByName(name:String): LiveData<Team> {
        return repository.getTeamByName(name)
    }

    fun setCalculate(calc:Boolean){
        calculate.value=calc
    }

    fun setWinner(winner:Boolean){
        radWin.value=winner
    }

    fun setRadiant(rad:Int){
        radScore.value=rad
    }

    fun setDire(dire:Int){
        direScore.value=dire
    }

    fun getTournamentTeams(): LiveData<List<TournamentTeam>> {
        return  repositoryClosed.getTournamentTeams()
    }

    fun getCLosedPlayoffScore(): LiveData<List<MatchScore>> {
        return  preMatchRepository.getScore()
    }

    fun insetNewScore(score :MatchScore){
        preMatchRepository.insertScore(score)
    }

    fun updateScore(score :List<MatchScore>){
        preMatchRepository.updateScore(score)
    }

    fun updateTeams(teams:List<TournamentTeam>){
        repositoryClosed.updateTeams(teams)
    }

}