package com.miklesam.dotamanager.ui.closedquali

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import com.miklesam.dotamanager.room.closedquali.ClosedQualiDao
import com.miklesam.dotamanager.room.closedquali.ClosedQualiDatabase
import com.miklesam.dotamanager.room.teams.TeamsDao
import com.miklesam.dotamanager.room.teams.TeamsDatabase

class ClosedRepository(application: Application){
    private var closedDao: ClosedQualiDao
    private var allTeams: LiveData<List<TournamentTeam>>
    init {
        val database: ClosedQualiDatabase = ClosedQualiDatabase.getInstance(application.applicationContext)
        closedDao = database.noteDao()
        allTeams = closedDao.getAllClosedTeams()
    }
    fun getTournamentTeams(): LiveData<List<TournamentTeam>> {
        return allTeams
    }
    fun initTournamentsTeams(list: List<TournamentTeam>){
        closedDao.insertData(list)
    }
    fun nukeClosed(){
        closedDao.nukeTable()
        Log.w("Nuke","Closed nuked")
    }
}