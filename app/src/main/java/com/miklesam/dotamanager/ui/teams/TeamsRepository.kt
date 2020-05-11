package com.miklesam.dotamanager.ui.teams

import android.app.Application
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.room.TeamsDao
import com.miklesam.dotamanager.room.TeamsDatabase

class TeamsRepository(application: Application){
    private var teamsDao: TeamsDao
    private var allTeams: LiveData<List<Team>>
    init {
        val database: TeamsDatabase = TeamsDatabase.getInstance(application.applicationContext)
        teamsDao = database.noteDao()
        allTeams = teamsDao.getAllTeams()
    }
    fun getTeams(): LiveData<List<Team>> {
        return allTeams
    }

    fun getTeamByName(name:String): LiveData<Team> {
        return teamsDao.getTeamByName(name)
    }
}